<template>
    <div>
        <div style="margin: 20px 0px;">
            <!-- input -->
            <el-input placeholder="请输入内容" v-model="keyword" clearable style="width: 20%;"> </el-input>
            <!-- 模糊搜索功能 -->
            <el-button type="primary" @click="query">搜索</el-button>

            <!-- 新增功能 -->
            <el-button type="success" @click="dialogVisible = true">新增</el-button>
            <el-dialog title="新增/修改" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
                <el-form ref="form" :model="form" label-width="80px">

                    <el-form-item label="疫苗名称">
                        <!-- <el-input v-model="form.typeName"></el-input> -->
                        <el-select v-model="form.vaccinumId" placeholder="请选择" style="width: 100%">
                            <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
                            </el-option>
                        </el-select>

                    </el-form-item>
                    <el-form-item label="可接种数量">
                        <el-input v-model="form.nums"></el-input>
                    </el-form-item>
                    <el-form-item label="已接种数量">
                        <el-input v-model="form.appNum"></el-input>
                    </el-form-item>
                    <el-form-item label="接种地点">
                        <el-input v-model="form.address"></el-input>
                    </el-form-item>

                    <el-form-item label="预约日期">
                        <el-date-picker v-model="form.appDate" align="right" type="date" placeholder="选择日期" size="large"
                            format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" :picker-options="pickerOptions">
                        </el-date-picker>
                    </el-form-item>

                    <el-form-item label="创建时间">
                        <el-date-picker v-model="form.postTime" align="right" type="date" placeholder="选择日期" size="large"
                            format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" :picker-options="pickerOptions">
                        </el-date-picker>
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
            <el-table-column prop="vaccinumName" label="疫苗名称"> </el-table-column>
            <el-table-column prop="nums" label="可接种数量"> </el-table-column>
            <el-table-column prop="appNum" label="已预约数量"> </el-table-column>
            <el-table-column prop="address" label="接种地点"> </el-table-column>
            <el-table-column prop="appDate" label="预约日期"> </el-table-column>
            <el-table-column prop="postTime" label="创建时间"> </el-table-column>

            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button size="mini" native-type @click="appointable(scope.row.id)">预约</el-button>
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
import Cookies from "js-cookie";


export default {
    //data表示vue对象中存储的数据
    data() {
        return {
            options: [],
            dialogVisible: false,
            tableData: [],
            pageNum: 1,
            pageSize: 3,
            total: 0,
            keyword: "",
            form: {
                id: '',
                vaccinumId: '',
                vaccinumName: '',
                nums: '',
                appNum: '',
                address: '',
                appDate: '',
                postTime: '',
            },
            formLabelWidth: '120px',
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now();
                },
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },



        };
    },
    //created页面加载完触发的生命周期
    created() {
        this.query();

        request

            .get("/gec/vaccinum/queryAll")

            .then((res) => {
                this.options = res.list;
            });

    },


    methods: {

        //预约
        appointable(appointableid) {
            let userphone = Cookies.get("phone");
            request
                .get("/gec/appointable-list/appointable", {
                    params: {
                        id: appointableid,
                        phone: userphone,
                    },
                })
                // then表示请求后的回调函数
                .then((res) => {
                    this.query();

                    if (res.ok) {
                        this.$message({
                            type: 'success',
                            message: '预约成功!'
                        });
                    } else {
                        this.$message({
                            type: 'error',
                            message: '预约失败!'
                        });
                    }
                });

        },

        // 重载方法
        query() {
            //发起一个异步请求，查询分类的数据
            request
                // get表示指定请求地址 和 请求参数
                .get("/gec/appointable-list/list", {
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
                    vaccinumId: '',
                    vaccinumName: '',
                    nums: '',
                    appNum: '',
                    address: '',
                    appDate: '',
                    postTime: '',

                };

            }).catch(() => { }
            );
        },

        handleCloseAfter() {
            this.dialogVisible = false;
            this.form = {
                id: '',
                vaccinumId: '',
                vaccinumName: '',
                nums: '',
                appNum: '',
                address: '',
                appDate: '',
                postTime: '',

            };
        },
        save() {

            if (this.form.id == "") {
                request
                    .post("/gec/appointable-list/insert", this.form)
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '新增成功!'
                            });
                            this.dialogVisible = false;
                            this.form = {
                                id: '',
                                vaccinumId: '',
                                vaccinumName: '',
                                nums: '',
                                appNum: '',
                                address: '',
                                appDate: '',
                                postTime: '',
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
                    .post("/gec/appointable-list/update", this.form)
                    .then((res) => {
                        if (res.ok == true) {
                            this.$message({
                                type: 'success',
                                message: '修改成功!'
                            });
                            this.dialogVisible = false;
                            this.form = {
                                id: '',
                                vaccinumId: '',
                                vaccinumName: '',
                                nums: '',
                                appNum: '',
                                address: '',
                                appDate: '',
                                postTime: '',

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
            this.form.vaccinumId = row.vaccinumId;
            this.form.vaccinumName = row.vaccinumName;
            this.form.nums = row.nums;
            this.form.appNum = row.appNum;
            this.form.address = row.address;
            this.form.appDate = row.appDate;
            this.form.postTime = row.postTime;

            this.dialogVisible = true;
            // this.form = row;
        },

        //删除方法
        handleDelete(index, row) {

            this.$confirm('此操作将永久删除该疫苗, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                //删除操作
                request
                    .get("/gec/appointable-list/delete", {
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
.el-date-editor.el-input,
.el-date-editor.el-input__inner {
    width: 100%;
}
</style>
    