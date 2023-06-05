<template>
    <div>
        <div style="margin: 20px 0px;">
            <!-- input -->
            <el-input placeholder="请输入疫苗类型进行搜索" v-model="keyword" clearable style="width: 20%;"> </el-input>
            <!-- 模糊搜索功能 -->
            <el-button type="primary" @click="query">搜索</el-button>

            <!-- 新增功能 -->
            <el-button type="success" @click="dialogVisible = true">新增</el-button>

            <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="用户名">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input v-model="form.password"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="save">提 交</el-button>
                </span>
            </el-dialog>





        </div>

        <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="id" label="编码" width="180"> </el-table-column>
            <el-table-column prop="name" label="疫苗类型" width="180"> </el-table-column>
            <el-table-column prop="remark" label="描述"> </el-table-column>
            <el-table-column label="操作">
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
            form: {
                id: '',
                name: '',
                remark: '',
            },
            formLabelWidth: '120px',
        };
    },
    //created页面加载完触发的生命周期
    created() {
        this.query();

    },


    methods: {

        // 重载方法
        query() {
            //发起一个异步请求，查询分类的数据
            request
                // get表示指定请求地址 和 请求参数
                .get("/gec/vaccinum-type/list", {
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
                    remark: '',
                };

            }).catch(() => { }
            );
        },

        handleCloseAfter() {
            this.dialogVisible = false;
            this.form = {
                id: '',
                name: '',
                remark: '',
            };

        },

        //定义一个添加-修改操作的函数
        save() {
            //判断-区分新增和修改操作
            if (this.form.id == "") {
                request
                    // post 请求方式
                    .post("/gec/vaccinum-type/insert", this.form)
                    // then表示请求后的回调函数
                    .then((res) => {
                        if (res.ok == true) {
                            this.query();
                            this.$message({
                                type: 'success',
                                message: '新增成功!'
                            });
                            this.dialogVisible = false;
                            this.form = {
                                id: "",
                                name: "",
                                remark: "",
                            };
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
                    .post("/gec/vaccinum-type/update", this.form)
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '修改成功!'
                            });
                            this.dialogVisible = false;
                            this.form = {
                                id: '',
                                name: '',
                                remark: '',
                            };
                            this.query();
                        } else {
                            this.$message({
                                type: 'error',
                                message: '修改失败!'
                            });
                        }
                    });

            }

        },

        handleEdit(row) {
            this.form.id = row.id;
            this.form.name = row.name;
            this.form.remark = row.remark;

            this.dialogVisible = true;


        },

        //删除方法
        handleDelete(index, row) {

            this.$confirm('此操作将永久删除该疫苗类型, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                //删除操作
                request
                    .get("/gec/vaccinum-type/delete", {
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
  