<template>
    <div class="login">
        <el-form ref="loginForm" :model="loginForm" class="login-form">
            <h3 class="title">Medical疫苗接种管理系统</h3>
            <el-form-item prop="phone">
                <el-input v-model="loginForm.phone" type="text" placeholder="账号">
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="loginForm.password" type="password" placeholder="密码">
                </el-input>
            </el-form-item>
            <!-- 验证码 -->
            <el-form-item prop="code" v-if="captchaEnabled">
                <el-input v-model="loginForm.code" placeholder="验证码" style="width: 63%">
                </el-input>
                <div class="login-code">
                    <img :src="codeUrl" @click="getCode" class="login-code-img" />
                </div>
            </el-form-item>

            <el-form-item style="width: 100%">
                <el-button size="medium" type="primary" style="width: 100%" @click="doLogin()">
                    <span>登 录</span>
                </el-button>
            </el-form-item>
            <el-form-item style="width: 100%">
                <el-button size="medium" type="primary" style="width: 100%" @click="handleEdit()">
                    <span>注 册</span>
                </el-button>
            </el-form-item>
        </el-form>

        <!--  底部  -->
        <div class="el-login-footer">
            <span>Copyright © 2018-2022 gec All Rights Reserved.</span>
        </div>
        <!-- 对话框 -->
        <el-dialog :visible.sync="dialogVisible" title="注册账号" width="30%">
            <el-form :model="form" label-width="120px">
                <!-- ref="form" :rules="registerRules"  -->
                <el-form-item label="姓名" prop="registrname">
                    <el-input v-model="form.name" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="registerphone">
                    <el-input v-model="form.phone" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="registerpassword">
                    <el-input v-model="form.password" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="头像">
                    <!-- action表示为上传文件的url   -->
                    <el-upload class="avatar-uploader" action="http://localhost:8088/file/upload/" :show-file-list="false"
                        :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="身份证号" prop="registeridcard">
                    <el-input v-model="form.code" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="registeremail">
                    <el-input v-model="form.email" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="registrsex">
                    <el-radio v-model="form.sex" label="男">男</el-radio>
                    <el-radio v-model="form.sex" label="女">女</el-radio>
                </el-form-item>
                <el-form-item label="年龄" prop="registrage">
                    <el-input v-model="form.age" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="职业" prop="registrjob">
                    <el-input v-model="form.job" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="handleCloseAfter()">取消</el-button>
                    <el-button type="primary" @click="save('form')">确定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
  
<script>
import request from "@/utils/request";
import Cookies from "js-cookie";
import router from "@/router";

export default {
    name: "Login",
    data() {
        return {


            codeUrl: "",
            loginForm: {
                phone: "12345678901",
                password: "123",
                code: "",
            },
            form: {
                name: "",
                phone: "",
                password: "",
                image: "",
                code: "",
                email: "",
                sex: "",
                age: "",
                job: "",
            },
            // registerRules: {
            //     registername: [
            //         { require: true, trigger: blur, message: "姓名不能为空" },
            //         { min: 2, max: 12, message: "姓名必须在2~12字之间" }
            //     ],
            //     registeremail: [
            //         { type: 'email', message: '请输入正确的邮箱地址', trigger: 'change' }
            //     ],
            //     registerage: [
            //         { required: true, message: '年龄不能为空' },
            //         { type: 'number', message: '年龄必须为数字值' },
            //         { min: 1, message: '年龄必须大于0' }
            //     ],

            // },
            imageUrl: "",
            loading: false,
            dialogVisible: false,
            // 验证码开关
            captchaEnabled: true,
            // 注册开关
            register: true,
        };
    },
    created() {
        this.getCode();
        //this.getCookie();
    },
    methods: {
        //验证码初始化
        getCode() {
            request
                .get("/captcha/getCaptcha")
                .then((res) => {
                    console.log("res:", res);
                    if (res.code == "200") {
                        this.codeUrl = "data:image/jpg;base64," + res.imgStr;
                    }
                });
        },


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
        //注册函数
        handleEdit() {
            this.form = {};
            //3.显示对话框
            this.dialogVisible = true;
        },
        //登录
        doLogin() {
            //发起一个异步请求，查询分类的数据
            request
                // get表示指定请求地址 和 请求参数
                .get("/gec/user/login", {
                    params: {
                        phone: this.loginForm.phone,
                        password: this.loginForm.password,
                        code: this.loginForm.code,
                    },
                })
                // then表示请求后的回调函数
                .then((res) => {
                    if (res.ok == true) {

                        Cookies.set("phone", this.loginForm.phone);
                        //消息提示
                        this.$message({
                            message: "登录成功",
                            type: "success",
                        });
                        //Cookies数据存储
                        Cookies.set("user", res.user, { expires: 0.2 }); //单位天
                        this.form = {};
                        this.dialogVisible = false;
                        //成功后跳转页面【使用路由来跳转页面】
                        router.push("/");
                    } else {
                        //登录失败
                        this.$message.error(res.message);
                    }
                });

        },

        handleCloseAfter() {
            this.form = {
                name: "",
                phone: "",
                password: "",
                image: "",
                code: "",
                email: "",
                sex: "",
                age: "",
                job: "",
            };
            this.imageUrl = "";
            this.dialogVisible = false;
        },

        save(formName) {
            console.log(this.form);
            request
                .post("/gec/user/register", this.form)
                .then((res) => {
                    if (res.ok == true) {
                        this.$message({
                            type: 'success',
                            message: '注册成功!'
                        });
                        this.form = {
                            name: "",
                            phone: "",
                            password: "",
                            image: "",
                            code: "",
                            email: "",
                            sex: "",
                            age: "",
                            job: "",
                        };
                        this.imageUrl = "";
                        this.dialogVisible = false;
                    } else {
                        this.$message({
                            type: 'error',
                            message: '注册失败!'
                        });
                    }
                });

        },
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

.login {
    position: absolute;
    width: 100%;
    height: 100%;
    overflow: hidden;
    background-image: url("../assets/welcome.jpg");
    background-size: 100% 100%;
    background-repeat: no-repeat;
}

.title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
}

.login-form {
    margin: 150px auto;
    background-color: rgba(90, 187, 211, 0.7);
    border-radius: 20px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;
}

.el-input {
    height: 38px;
}

input {
    height: 38px;
}

.input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
}

.login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
}

.login-code {
    width: 33%;
    height: 38px;
    float: right;
}

.el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
}

.login-code-img {
    height: 38px;
}
</style>