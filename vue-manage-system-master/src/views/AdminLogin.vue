<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">管理员入口</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="username" placeholder="username">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              type="password"
              placeholder="password"
              v-model="password"
              @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
        <p class="login-tips"></p>
      </el-form>
    </div>
  </div>
</template>

<script>
//import "@/api/login.js"
import {adminLogin} from "../api/login";

export default {
  data() {
    return {
        username: "",
        password: "",
      rules: {
        username: [
          {required: false, message: "请输入用户名", trigger: "blur"}
        ],
        password: [
          {required: false, message: "请输入密码", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.$store.commit("clearTags");
  },
  methods: {
    submitForm() {
      let user = {user_name: this.username, user_password: this.password};
      console.log(user);
      adminLogin(user).then(response => {
        if (response.data.code === 0) {
          this.$message({
            message: "登录成功",
            type: "success"

          });
          // 使用localStorage 存储数据
          // setToken(response.data.data.token);
          // setList(JSON.stringify(response.data.data.result));
          // setUserId(JSON.stringify(response.data.data.userId));
          // setUserName(JSON.stringify(this.username));
          localStorage.setItem("ms_username", this.username);
          localStorage.setItem("ms_type", 1); //1->admin
          this.$router.push("/dashboard");
        } else if (response.data.code === -2) {
          this.$message.error("用户名不存在");
        } else if (response.data.code === -3) {
          this.$message.error("密码错误");
        }
      });
      return false;
    },
  }
}
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  margin-bottom: 100px;
  background-image: url(../assets/img/neu6.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>