<template>
    <div>
        <div style="margin: 20px 0px;">
            <!-- input -->
            <el-input placeholder="请输入身份证号码进行搜索" v-model="keyword" clearable style="width: 20%;"> </el-input>
            <!-- 模糊搜索功能 -->
            <el-button type="primary" @click="query">搜索</el-button>

            <!-- 新增功能 -->
            <el-button type="success" @click="dialogVisible = true">新增</el-button>
            <el-dialog title="新增/编辑" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="姓名">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码">
                        <el-input v-model="form.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="头像">
                        <!-- action表示为上传文件的url   -->
                        <el-upload class="avatar-uploader" action="http://localhost:8088/file/upload/"
                            :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="form.password"></el-input>
                    </el-form-item>
                    <el-form-item label="角色">
                        <el-select v-model="form.role" placeholder="请选择" style="width: 100%">
                            <el-option v-for="item in options" :key="item.role" :label="item.label" :value="item.role">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="身份证号">
                        <el-input v-model="form.code"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="form.email"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="registrsex">
                        <el-radio v-model="form.sex" label="男">男</el-radio>
                        <el-radio v-model="form.sex" label="女">女</el-radio>
                    </el-form-item>
                    <el-form-item label="年龄">
                        <el-input v-model="form.age"></el-input>
                    </el-form-item>
                    <el-form-item label="职业">
                        <el-input v-model="form.job"></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="handleCloseAfter">取 消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                </div>
            </el-dialog>
        </div>

        <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="userId" label="id"> </el-table-column>
            <el-table-column prop="userName" label="姓名"> </el-table-column>
            <el-table-column prop="code" label="身份证号"> </el-table-column>
            <el-table-column prop="email" label="邮箱"> </el-table-column>
            <el-table-column prop="sex" label="性别"> </el-table-column>
            <el-table-column prop="age" label="年龄"> </el-table-column>
            <el-table-column prop="job" label="职业"> </el-table-column>
            <el-table-column prop="status" label="通行码">
                <!-- scope表示为作用域  scope.row表示为作用域中这一行的数据 -->
                <template slot-scope="scope">
                    <el-button size="small" @click="showStauts(scope.row.userId)" type="success"
                        v-if="scope.row.status == 0">绿码</el-button>
                    <el-button size="small" @click="showStauts(scope.row.userId)" type="warning"
                        v-if="scope.row.status == 1">黄码</el-button>
                    <el-button size="small" @click="showStauts(scope.row.userId)" type="danger"
                        v-if="scope.row.status == 2">红码</el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button size="mini" native-type @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" native-type type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 定义一个分页标签 -->
        <div>
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[3, 8, 15, 30]" :page-size="3" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <!-- 定义一个对话框 -->
        <div>
            <el-dialog title="用户的通行码" :visible.sync="dialogQrCodeVisible" width="30%" :before-close="handleQrCodeClose">
                <img :src="QrCode" />
            </el-dialog>
        </div>

    </div>
</template>
    
<script>
//导入request工具
import request from "@/utils/request";


export default {
    //data表示vue对象中存储的数据
    data() {
        return {
            dialogVisible: false,
            dialogQrCodeVisible: false,
            QrCode: '',
            tableData: [],
            pageNum: 1,
            pageSize: 3,
            total: 0,
            keyword: "",
            imageUrl: '',
            form: {
                userId: '',
                name: '',
                phone: '',
                image: '',
                password: '',
                role: '',
                code: '',
                email: '',
                sex: '',
                age: '',
                job: '',
                status: '',
            },
            options: [
                {
                    role: 1,
                    label: '用户'
                }, {
                    role: 2,
                    label: '医护'
                },
            ],
            formLabelWidth: '120px',


        };
    },
    //created页面加载完触发的生命周期
    created() {
        this.query();

    },


    methods: {


        //成功后的处理函数
        handleAvatarSuccess(res, file) {
            console.log("file===" + file);
            this.imageUrl = URL.createObjectURL(file.raw);
            this.form.image = res;
        },
        //上传之前的处理函数
        beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg";
            const isPNG = file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 2MB!");
            }
            console.log("isJPG===" + ((isJPG || isPNG) && isLt2M));
            return (isJPG || isPNG) && isLt2M;
        },
        // 重载方法
        query() {
            //发起一个异步请求，查询分类的数据
            request
                // get表示指定请求地址 和 请求参数
                .get("/gec/user-info/list", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        keyWord: this.keyword,
                    },
                })
                // then表示请求后的回调函数
                .then((res) => {
                    console.log(res);
                    // 把后台的响应的数据赋值给data中的tableData
                    this.tableData = res.list;
                    this.total = res.total;
                });
        },
        //二维码对话框  
        showStauts(id) {
            //1、发起二维码的请求
            this.QrCode = "http://localhost:8088/code?id=" + id;
            //2、显示对话框
            this.dialogQrCodeVisible = true;
        },
        handleQrCodeClose() {
            this.dialogQrCodeVisible = false;
            this.QrCode = '';
        },

        //修改/新增对话框
        handleClose() {
            this.$confirm('是否退出?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.dialogVisible = false;
                this.form = {
                    userId: '',
                    name: '',
                    phone: '',
                    image: '',
                    password: '',
                    role: '',
                    code: '',
                    email: '',
                    sex: '',
                    age: '',
                    job: '',
                    status: '',
                };
                this.imageUrl = "";
            }).catch(() => { }
            );
        },

        handleCloseAfter() {
            this.form = {
                userId: '',
                name: '',
                phone: '',
                image: '',
                password: '',
                role: '',
                code: '',
                email: '',
                sex: '',
                age: '',
                job: '',
                status: '',
            };
            this.imageUrl = "";
            this.dialogVisible = false;
        },


        save() {
            console.log(this.form);

            if (this.form.userId == "") {

                request
                    .post("/gec/user-info/insert", this.form)
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '新增成功!'
                            });
                            this.dialogVisible = false;
                            this.form = {
                                userId: '',
                                name: '',
                                name: '',
                                phone: '',
                                image: '',
                                password: '',
                                role: '',
                                code: '',
                                email: '',
                                sex: '',
                                age: '',
                                job: '',
                                status: '',
                            };
                            this.imageUrl = "";
                            this.query();
                        } else {
                            this.$message({
                                type: 'error',
                                message: '新增失败!'
                            });
                        }
                    });
            } else {
                request
                    .post("/gec/user-info/update", this.form)
                    .then((res) => {
                        console.log(this.form);

                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '修改成功!'
                            });
                            this.dialogVisible = false;
                            this.form = {
                                userId: '',
                                name: '',
                                phone: '',
                                image: '',
                                password: '',
                                role: '',
                                code: '',
                                email: '',
                                sex: '',
                                age: '',
                                job: '',
                                status: '',

                            };
                            this.query();
                        } else {
                            this.$message({
                                type: 'error',
                                message: '修改失败!'
                            });
                        }
                    }).catch((res) => {

                        console.log(res.ok);
                    });


            }

        },

        handleEdit(row) {
            console.log(row);
            this.dialogVisible = true;
            this.form.userId = row.userId;
            this.imageUrl = row.user.image;
            this.form.id = row.userId;
            this.form.name = row.userName;
            this.form.phone = row.user.phone;
            this.form.image = row.user.image;
            this.form.password = row.user.password;
            this.form.role = row.user.role;
            this.form.email = row.email;
            this.form.code = row.code;
            this.form.name = row.user.name;
            this.form.sex = row.sex;
            this.form.age = row.age;
            this.form.job = row.job;
            this.form.status = row.status;

            // this.form = row;
        },

        //删除方法
        handleDelete(index, row) {

            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                //删除操作
                request
                    .get("/gec/user-info/delete", {
                        params: {
                            id: row.userId
                        },
                    })
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            this.query();
                        } else {
                            this.$message({
                                type: 'error',
                                message: '删除失败!'
                            });
                        }
                    });

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        //修改单页数据数量
        handleSizeChange(val) {
            this.pageNum = 1;
            this.pageSize = val;
            this.query();
        },
        //跳转页码
        handleCurrentChange(val) {
            console.log(val);
            this.pageNum = val;
            this.query();
        }



    },
};
</script>
<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}

.el-date-editor.el-input,
.el-date-editor.el-input__inner {
    width: 335px;
}
</style>
    