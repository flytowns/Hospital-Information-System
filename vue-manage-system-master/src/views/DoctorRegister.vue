<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">医生信息注册</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="cardid">
          <el-input v-model="cardid" placeholder="身份证号码">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input v-model="name" placeholder="名字">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="age">
          <el-input v-model="age" placeholder="年龄">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="tele">
          <el-input v-model="tele" placeholder="电话">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>


        <el-form-item prop="gender">
          <el-select v-model="gender" placeholder="性别" style="width: 640px">
            <span style="width: 700px"> </span>
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              <!--              <span style="float: left">{{ item.label }}</span>-->
              <!--              <span style="float: right; color: #8492a6; font-size: 10px">{{ item.value }}</span>-->
            </el-option>

          </el-select>
          <!--          <el-input v-model="gender" placeholder="性别，请输入0/1，0代表女，1代表男">-->
          <!--            <template #prepend>-->
          <!--              <el-button icon="el-icon-user"></el-button>-->
          <!--            </template>-->
          <!--          </el-input>-->
        </el-form-item>

        <el-form-item prop="username">
          <el-input v-model="username" placeholder="用户名">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="密码">
          <el-input
              type="password"
              placeholder="密码"
              v-model="password"
              @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">注册</el-button>
        </div>
        <p class="login-tips"></p>
      </el-form>
    </div>
  </div>
</template>

<script>
//import "@/api/login.js"
import {doctorRegister} from "../api/admin";

export default {
  data() {
    return {
      options: [{
        value: "男",
        label: '男'
      }, {
        value: "女",
        label: '女'
      }],
      name: "",
      age: "",
      tele: "",
      gender: "",
      username: "",
      password: "",
      cardid: "",
      rules: {
        cardid: [
          {required: false, message: "请输入身份证号码", trigger: "blur"}
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
      if(this.cardid === "" || this.username === "" || this.name === "" || this.tele=== "" || this.password=== "" ||this.age=== "" ){
        this.$message.error("请将信息填写完整");
        return false;
      }
      let user = {
        name: this.name,
        age: Number(this.age),
        tele: this.tele,
        gender: this.gender,
        userName: this.username,
        userPassword: this.password,
        cardId: this.cardid
      };
      console.log(user);
      doctorRegister(user).then(response => {
        if (response.data.code === 0) {
          this.$message({
            message: "注册成功",
            type: "success"

          });
          // 使用localStorage 存储数据
          // setToken(response.data.data.token);
          // setList(JSON.stringify(response.data.data.result));
          // setUserId(JSON.stringify(response.data.data.userId));
          // setUserName(JSON.stringify(this.username));
          //localStorage.setItem("ms_username", this.cardid); //连接下一个页面右上角的名字
          this.$router.replace("/table");
        } else if (response.data.code === -4) {
          this.$message.error("该身份证号已经注册存在");
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
  background-image: url(../assets/img/neu13.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #222222;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 37%;
  top: 37%;
  width: 700px;
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