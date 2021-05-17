<template>
  <el-col>
    <el-carousel :interval="2000" type="card" height="400px" style="width: 1475px">
      <el-carousel-item v-for="item in 6" :key="item">
<!--        <h3 class="medium">{{ item }}</h3>-->
        <img :src=this.imgHorse[item] class="horse">
      </el-carousel-item>
    </el-carousel>
    <el-row>
      <el-col :span="6" v-for="(o, index) in 3" :key="o" :offset="index > 0 ? 3 : 0">
        <el-card :body-style="{ padding: '20px' }">
          <div style="padding: 0px;">
            <div class="bottom">
              <el-button type="primary" plain v-bind:id="buttonNames"  class="button" @click="submitForm(o)">
                {{ buttonNames[o] }}
              </el-button>
            </div>
          </div>

<!--          <img src="@/assets/img/patient.png" class="image"/>-->
          <img :src=this.imgSrcs[o] class="image"/>

        </el-card>
      </el-col>
    </el-row>
  </el-col>
</template>

<script>
export default {
  data() {
    return {
      name: "Intro",
      buttonNames: {
        "1": "患者入口",
        "2": "医生入口",
        "3": "管理员入口",
      },
      imgSrcs: {
        "1": require("@/assets/img/patient.png"),
        "2": require("@/assets/img/doctor.png"),
        "3": require("@/assets/img/admin.png"),
      },
      imgHorse:{
        "1": require("@/assets/img/neu1.jpg"),
        "2": require("@/assets/img/neu5.jpg"),
        "3": require("@/assets/img/neu6.jpg"),
        "4": require("@/assets/img/neu7.jpg"),
        "5": require("@/assets/img/neu10.jpg"),
        "6": require("@/assets/img/neu11.jpg"),
      }
    };
  },
  created() {
    this.$store.commit("clearTags");
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    submitForm(x) {

      // this.$refs.login.validate(valid => {
      //   if (valid) {
      //     this.$message.success("登录成功");
      //     localStorage.setItem("ms_username", this.param.username);
      //     this.$router.push("/login"); //进入下一个层次
      //   } else {
      //     this.$message.error("请输入账号和密码");
      //     return false;
      //   }
      // });
      // console.log("click");
      if (x == '1') {
        this.$router.replace("/patient_login");
      } else if (x == '2') {
        this.$router.replace("/doctor_login");
      } else if (x == '3') {
        this.$router.replace("/admin_login");
      }
    }
  }
}
</script>


<style>

/*.el-carousel__item h3 {*/
/*  !*color: #eeeeee;*!*/
/*  font-size: 14px;*/
/*  opacity: 0.75;*/
/*  line-height: 400px;*/
/*  margin: 20px;*/
/*  background-image: url(../assets/img/neu1.jpg);*/
/*  background-size: 750px, 500px;*/
/*  box-shadow:0 5px 20px rgba(0,0,0,0.5)*/
/*}*/

.el-carousel__item:nth-child(2n) {
  background-color: #ffffff;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #ffffff;
}
.bottom {
  margin-top: 12px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;

}

.button {
  padding: 50px;
  width: 500px;
  height: 90px;
  display: flex;
  /* 居中*/
  justify-content: space-between;
  text-align: center;
  /*按钮上的文字居中*/
  text-indent: 70px;
  /*text-emphasis: #eeeeee;*/
  font-size: 18px;
}
.horse{
  width: 700px;
  height: 450px;
  opacity: 0.85;
  margin: 20px;
  /*margin: 20px;*/
  /*margin-right: 10px;*/
  /*margin-left: 10px;*/
  /*margin-inline: 20px;*/
  /*margin-block: 20px;*/
  /*margin-inline-start: 20px;*/


  box-shadow:0px 0px 15px rgba(0,0,0,0.8)
  /*line-height: 400px;*/
  /*font-size: 14px;*/
  /*opacity: 0.75;*/
  /*line-height: 400px;*/
  /*margin: 20px;*/
  /*!*background-image: url(../assets/img/neu1.jpg);*!*/
  /*background-size: 750px, 500px;*/
  /*box-shadow:0 5px 20px rgba(0,0,0,0.5)*/
}

.image {
  margin-left: 80px;
  width: 50%;
  height: 50%;
  margin-top: 10px;
  margin-bottom: 30px;
  /*height: 230px;*/

  display: block;
}
</style>