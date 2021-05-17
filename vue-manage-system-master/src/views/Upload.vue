<template>
  <div class="login-wrap">
    <!--        <div class="container">-->
    <!--    <el-container style="text-align: center" >-->
    <el-container style="text-align: center">
      <el-header style="height: 190px">
        <!--        <div class="content-title">Header:</div>-->
        <el-button type="primary" plain class="button2" @click="download">患者影像下载</el-button>
      </el-header>
      <el-main>
        <div class="content-title" ></div>
        <el-upload

            class="upload-demo"
            drag
            accept="application/zip"
            action="http://localhost:8888/upload/"
            :on-success="handleAvatarSuccess"
            :before-upload="onBeforeUpload"
            :data="uploadData"
            multiple
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text" style="font-size: 20px">
            将患者医学影像文件拖到此处进行上传 或
            <em>点击上传</em>
          </div>
          <template #tip>
            <div class="el-upload__tip" style="font-size: 20px">只能上传 zip 文件</div>
          </template>
        </el-upload>

      </el-main>
      <el-footer>
        <el-button type="primary" plain class="button2" @click="deleteSeries">患者影像删除</el-button>
      </el-footer>
    </el-container>

  </div>

  <!--    </div>-->
</template>

<script>

import "cropperjs/dist/cropper.css";
import {deleteSeries, download} from "../api/admin";


export default {
  name: "upload",
  inject: ['reload'],
  data() {
    return {
      imageUrl: '',
      defaultSrc: require("../assets/img/img.jpg"),
      fileList: [],
      imgSrc: "",
      cropImg: "",
      dialogVisible: false,
      patient_card_id: localStorage.getItem("ms_patient_card_id"),
      uploadData: {
        'patient_card_id': localStorage.getItem("ms_patient_card_id").toString()
      },
    };
  },
  components: {
    // VueCropper
  },

  methods: {

    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log("this.imageUrl", this.imageUrl);
      console.log("file.name", file.name);



      this.$message.success("上传成功")
    },
    download() {
      download(this.patient_card_id).then(res => {
        console.log("res", res);
        if (res.data === "") {
          this.$message.error("该患者影像尚未上传");
        } else {
          this.$message.success("下载成功");
        }
      });

    },
    deleteSeries() {
      if (localStorage.getItem("ms_type") == 3) {
        this.$message.error("没有权限");
      } else if (localStorage.getItem("ms_type") == 1 || localStorage.getItem("ms_type") == 2) {
        // 二次确认删除
        this.$confirm("确定要删除吗？", "提示", {
          type: "warning"
        })
            .then(() => {
              deleteSeries(this.patient_card_id).then(res => {
                console.log("res.data", res.data);
                console.log("res.data.data", res.data.data);
                if (res.data.code == 0) {
                  //console.log("final type message: ", this.tableData);
                  this.$message.success("删除成功");
                } else if (res.data.code == -7) {
                  this.$message.error("该患者影像尚未上传");
                } else {
                  this.$message.error("删除失败");
                }
              })
            })
            .catch(() => {
            });
      }


    },
     onBeforeUpload(file) {
      const isIMAGE = file.type === 'application/x-zip-compressed'
      console.log("file.type", file.type)
      console.log("isImage", isIMAGE);
      if (localStorage.getItem("ms_type") == 3) {
        this.$message.error("没有权限")
        return false;
      } else if (!isIMAGE) {
        this.$message.error("文件格式错误")
        return false;
      } else {
        return true;
        // console.log("fuckingtfffffffffrue");
        // //todo 改用户已有影像的判断
        // isSeriesExist(this.patient_card_id).then(res => {
        //   console.log("res", res);
        //   console.log("res.data", res.data);
        //   console.log("res.data.code", res.data.code);
        //   if (res.data.code == -7) {
        //     this.$message.success("上传成功");
        //     return true;
        //   } else if (res.data.code == -2) {
        //     this.$message.error("该患者身份证号不存在");
        //     return false;
        //   }else if(res.data.code == 0 ){
        //     this.$message.error("该患者影像已存在，若想继续上传，需要先删除");
        //     return false;
        //   }});
      }
     // setTimeout(function(){  }, 50000);
   //   return false;

  },


  setImage(e) {
    const file = e.target.files[0];
    if (!file.type.includes("image/")) {
      return;
    }
    const reader = new FileReader();
    reader.onload = event => {
      this.dialogVisible = true;
      this.imgSrc = event.target.result;
      this.$refs.cropper &&
      this.$refs.cropper.replace(event.target.result);
    };
    reader.readAsDataURL(file);
  },
  cropImage() {
    this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
  },
  cancelCrop() {
    this.dialogVisible = false;
    this.cropImg = this.defaultSrc;
  },
  imageuploaded(res) {
    console.log(res);
  },
  handleError() {
    this.$notify.error({
      title: "上传失败",
      message: "图片上传接口上传失败，可更改为自己的服务器接口"
    });
  }
}
,
created()
{
  this.cropImg = this.defaultSrc;
}
}
;
</script>

<style scoped>
.upload-demo {


}

.button2 {
  width: 200px;
  height: 60px;
  margin-top: 70px;
  background-size: 200%;
  font-size: 26px;
  position: center;

}

.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  margin-top: 0px;
  background-image: url(../assets/img/neu10.jpg);
  background-size: 100%;
}

.content-title {
  font-weight: 400;
  line-height: 50px;
  margin: 10px 0;
  font-size: 22px;
  color: #1f2f3d;
}

.pre-img {
  width: 100px;
  height: 100px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 5px;
}

.crop-demo {
  display: flex;
  align-items: flex-end;
}

.crop-demo-btn {
  position: relative;
  width: 100px;
  height: 40px;
  line-height: 40px;
  padding: 0 20px;
  margin-left: 30px;
  background-color: #409eff;
  color: #fff;
  font-size: 14px;
  border-radius: 4px;
  box-sizing: border-box;
}

.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
</style>