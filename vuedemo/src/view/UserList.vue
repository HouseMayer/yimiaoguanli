<template>
    <div>
        <div style="margin: 20px 0px;">
            <!-- input -->
            <el-input placeholder="请输入内容" v-model="keyword" clearable style="width: 20%;"> </el-input>
            <!-- 模糊搜索功能 -->
            <el-button type="primary" @click="query">搜索</el-button>

            <!-- 新增功能 -->
            <el-button type="success" @click="dialogVisible = true">新增</el-button>
            <el-dialog title="新增/编辑" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="姓名">
                        <el-input v-model="form.name" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码">
                        <el-input v-model="form.phone" autocomplete="off"></el-input>
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
                        <el-input v-model="form.password" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="角色">
                        <el-select v-model="form.role" placeholder="请选择" style="width: 100%">
                            <el-option v-for="item in options" :key="item.role" :label="item.label" :value="item.role">
                            </el-option>
                        </el-select>
                    </el-form-item>


                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="handleCloseAfter">取 消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                </div>
            </el-dialog>
        </div>

        <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="id" label="id"> </el-table-column>
            <el-table-column prop="name" label="姓名"> </el-table-column>
            <el-table-column prop="phone" label="手机号码"> </el-table-column>
            <el-table-column prop="image" label="头像" width="150">
                <template slot-scope="scope">
                    <img :src="scope.row.image" style="width: 100px; height: 100px" />
                </template>
            </el-table-column>
            <el-table-column prop="password" label="密码"> </el-table-column>
            <el-table-column prop="role" label="角色">
                <template slot-scope="scope">
                    <span v-if="scope.row.role == 0">管理员</span>
                    <span v-if="scope.row.role == 1">用户</span>
                    <span v-if="scope.row.role == 2">医护</span>
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
            tableData: [],
            pageNum: 1,
            pageSize: 3,
            total: 0,
            keyword: "",
            imageUrl: '',
            options: [
                {
                    role: 0,
                    label: '管理员'
                }, {
                    role: 1,
                    label: '用户'
                }, {
                    role: 2,
                    label: '医护'
                },
            ],
            form: {
                id: '',
                name: '',
                phone: '',
                image: '',
                password: '',
                role: '',
                userId: '',
                code: '',
                email: '',
                sex: '',
                age: '',
                job: '',
                status: '',

            },
            formLabelWidth: '120px',


        };
    },
    //created页面加载完触发的生命周期
    created() {
        this.query();

    },


    methods: {
         //上传之前的处理函数
         beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg";
            const isPNG = file.type === "image/png";
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG && !isPNG) {
                this.$message.error("上传头像图片只能是 JPG 或者 PNG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 2MB!");
            }
            console.log("isJPG===" + ((isJPG || isPNG) && isLt2M));
            return (isJPG || isPNG) && isLt2M;
        },

        //成功后的处理函数
        handleAvatarSuccess(res, file) {
            console.log("file===" + file);
            this.imageUrl = URL.createObjectURL(file.raw);
            // 把上传图片的url set到form中
            this.form.image = res;  
        },
       


        // 重载方法
        query() {
            //发起一个异步请求，查询分类的数据
            request
                // get表示指定请求地址 和 请求参数
                .get("/gec/user/listInfo2", {
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
                    // 将res.total赋值给total
                    this.total = res.total;
                });
        },
        handleClose() {
            this.$confirm('是否退出?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.dialogVisible = false;
                this.form = {
                    id: '',
                    name: '',
                    phone: '',
                    image: '',
                    password: '',
                    role: '',
                    userId: '',
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
                id: '',
                name: '',
                phone: '',
                image: '',
                password: '',
                role: '',
                userId: '',
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
            //id为空，则是做新增操作
            if (this.form.id == "") {
                request
                //发起post异步请求、提交数据
                    .post("/gec/user/insert", this.form)
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '新增成功!'
                            });
                            //关闭对话框
                            this.dialogVisible = false;
                            //重新加载数据
                            this.query();
                        } else {

                            this.$message({
                                type: 'error',
                                message: '新增失败!'
                            });
                        }
                        this.imageUrl = "";
                        this.form = {
                                id: '',
                                name: '',
                                phone: '',
                                image: '',
                                password: '',
                                role: '',
                                userId: '',
                                code: '',
                                email: '',
                                sex: '',
                                age: '',
                                job: '',
                                status: '',

                            };
                    });
            } else {
                //id不为空，则是做修改操作
                request
                    .post("/gec/user/update", this.form)
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '修改成功!'
                            });
                            //关闭对话框
                            this.dialogVisible = false;
                            //重新加载数据
                            this.query();
                        } else {
                            this.$message({
                                type: 'error',
                                message: '修改失败!'
                            });
                        }
                         imageUrl = "";
                         this.form = {
                                id: '',
                                name: '',
                                phone: '',
                                image: '',
                                password: '',
                                role: '',
                                userId: '',
                                code: '',
                                email: '',
                                sex: '',
                                age: '',
                                job: '',
                                status: '',

                            };
                    });

            }

        },

        handleEdit(row) {
            console.log(row);
            this.dialogVisible = true;
            this.imageUrl = row.image;
            this.form.id = row.id;
            this.form.name = row.name;
            this.form.phone = row.phone;
            this.form.image = row.imageUrl;
            this.form.password = row.password;
            this.form.role = row.role;


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
                    .get("/gec/user/delete", {
                        params: { id: row.id },
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
    