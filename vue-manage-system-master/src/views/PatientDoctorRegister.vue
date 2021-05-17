<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">医生患者对应表注册</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="patient_card_id">
          <el-input v-model="patient_card_id" placeholder="患者身份证号码">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="doctor_card_id">
          <el-input v-model="doctor_card_id" placeholder="医生身份证号码">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">添加</el-button>
        </div>
        <p class="login-tips"></p>
      </el-form>
    </div>
  </div>
</template>

<script>
//import "@/api/login.js"
import {patientDoctorRegister} from "../api/admin";

export default {
  data() {
    return {
      patient_card_id:"",
      doctor_card_id: "",
      rules: {
        patient_card_id: [
          {required: false, message: "请输入患者身份证号码", trigger: "blur"}
        ],
        doctor_card_id: [
          {required: false, message: "请输入医生身份证号码", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.$store.commit("clearTags");
  },
  methods: {
    submitForm() {
      if(this.patient_card_id === "" || this.doctor_card_id === ""){
        this.$message.error("请将信息填写完整");
        return false;
      }

      let user = {
        id:"",
        doctorId:"",
        doctorCardId:this.doctor_card_id,
        doctorName:"",
        patientId:"",
        patientCardId:this.patient_card_id,
        patientName:"",
      };
      console.log(user);
      patientDoctorRegister(user).then(response => {
        if (response.data.code === 0) {
          this.$message({
            message: "添加成功",
            type: "success"

          });
          // 使用localStorage 存储数据
          // setToken(response.data.data.token);
          // setList(JSON.stringify(response.data.data.result));
          // setUserId(JSON.stringify(response.data.data.userId));
          // setUserName(JSON.stringify(this.username));
          //localStorage.setItem("ms_username", this.cardid); //连接下一个页面右上角的名字
          this.$router.replace("/table_patient_doctor_info");
        } else if (response.data.code === -4) {
          this.$message.error("该患者已经选择医生");
        }else if(response.data.code === -2){
          this.$message.error("该患者身份证号不存在");
        }else if(response.data.code === -6){
          this.$message.error("该医生身份证号不存在");
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
  background-image: url(../assets/img/neu7.jpg);
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