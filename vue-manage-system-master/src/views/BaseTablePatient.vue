<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 患者信息管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
            type="primary"
            icon="el-icon-delete"
            class="handle-del mr10"
            @click="delAllSelection"
        >批量删除
        </el-button>
        <el-select v-model="query.searchType" placeholder="查询指标" class="handle-select mr10">
          <el-option key="1" label="身份证号码" value=1></el-option>
          <el-option key="2" label="姓名" value=2></el-option>
        </el-select>

        <el-input v-model="query.word" placeholder="身份证号码/姓名" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <!--        <el-button type="primary" icon="el-icon-upload" @click="addDoctor" style="float: right">添加医生信息</el-button>-->
      </div>
      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="tele" label="电话"></el-table-column>
        <el-table-column prop="userName" label="用户名"></el-table-column>
        <el-table-column prop="diagnose" label="诊断报告"></el-table-column>
        <el-table-column prop="cardId" label="身份证号码"></el-table-column>
<!--        <el-table-column prop="seriesPath" label="影像文件"></el-table-column>-->
        <el-table-column label="影像文件" width="180" align="center">
          <template #default="scope">
            <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEditImg(scope.$index, scope.row)"
            >编辑
            </el-button>
<!--            <el-button-->
<!--                type="text"-->
<!--                icon="el-icon-delete"-->
<!--                class="red"-->
<!--                @click="handleDelete(scope.$index, scope.row)"-->
<!--            >删除-->
<!--            </el-button>-->
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
            >编辑
            </el-button>
            <el-button
                type="text"
                icon="el-icon-delete"
                class="red"
                @click="handleDelete(scope.$index, scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 编辑弹出框 管理员视角-->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.tele"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.gender"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.userPassword"></el-input>
        </el-form-item>
        <el-form-item label="诊断报告">
          <el-input v-model="form.diagnose"></el-input>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.cardId"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
      </template>
    </el-dialog>

    <!-- 编辑弹出框 医生视角-->
    <el-dialog title="编辑" v-model="editVisibleDoctor" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="诊断报告">
          <el-input v-model="form.diagnose"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisibleDoctor = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
      </template>
    </el-dialog>


    <!-- 编辑弹出框 患者视角-->
    <el-dialog title="编辑" v-model="editVisiblePatient" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.tele"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.gender"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.userPassword"></el-input>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.cardId"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisiblePatient = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
      </template>
    </el-dialog>


  </div>
</template>

<script>
import {
  deletePatientInfo, deletePatientInfoList,
  getAllPatientInfo,
  readPatientInfoByCardId, readPatientInfoByDoctorCardId, readPatientInfoByName,
  updatePatientInfo
} from "../api/admin";

export default {
  name: "basetable",
  data() {
    return {
      query: {
        searchType: "",
        word: "",


        name: "",
        cardId: "",
        address: "",
        pageIndex: 1,
        pageSize: 10
      },
      tableData: [
        {
          id: "",
          name: "",
          age: "",
          tele: "",
          gender: "",
          userName: "",
          userPassword: "",
          diagnose: "",
          cardId: ""
        }
      ],
      multipleSelection: [],
      delList: [],
      editVisible: false,
      editVisibleDoctor: false,
      editVisiblePatient: false,
      pageTotal: 0,
      form: {},
      idx: -1,
      id: -1
    };
  },
  created() {
    this.getData();
  },
  methods: {
    // 获取 easy-mock 的模拟数据
    getData() {
      //todo:这里写if else 实现 医生端和患者端 只能看到自己的tableData
      if (localStorage.getItem("ms_type") == 3) {//患者
        readPatientInfoByCardId(localStorage.getItem("ms_username")).then(res => {
          console.log(res.data.data);
          if (res.data.code == 0) {
            //list 为从后端导入的数据
            //todo:data的类型问题
            this.tableData = res.data.data;
            console.log("final type message: ", this.tableData);
          } else {
            this.$message.error("读取类型信息失败");
          }
        });
      }else if(localStorage.getItem("ms_type") == 2){//医生
        readPatientInfoByDoctorCardId(localStorage.getItem("ms_username")).then(res => {
          console.log(res.data.data);
          if (res.data.code == 0) {
            //list 为从后端导入的数据
            //todo:data的类型问题
            this.tableData = res.data.data;
            console.log("final type message: ", this.tableData);
          } else {
            this.$message.error("读取类型信息失败");
          }
        });
      } else if (localStorage.getItem("ms_type") == 1 ) {//管理员
        getAllPatientInfo().then(res => {
          console.log(res.data.data);
          if (res.data.code == 0) {
            //list 为从后端导入的数据
            //todo:data的类型问题
            this.tableData = res.data.data;
            console.log("final type message: ", this.tableData);
          } else {
            this.$message.error("读取类型信息失败");
          }
        });
      }

    },
    // 触发搜索按钮
    handleSearch() {
      if (localStorage.getItem("ms_type") == 3|| localStorage.getItem("ms_type") == 2) {
        this.$message.error("没有权限");
      } else if (localStorage.getItem("ms_type") == 1 ) {
        if (this.word !== "") {
          console.log("this.query.searchType: ", this.query.searchType);
          if (this.query.searchType === "1") {//身份证号搜索
            readPatientInfoByCardId(this.query.word).then(res => {
              console.log("res.data.data", res.data.data);
              if (res.data.code == 0) {
                //list 为从后端导入的数据
                //todo:data的类型问题
                this.tableData = res.data.data;
                console.log("final type message: ", this.tableData);
              } else {
                this.$message.error("读取类型信息失败");
              }
            })
          } else if (this.query.searchType === "2") {//名字搜索
            readPatientInfoByName(this.query.word).then(res => {
              console.log("res.data", res.data);
              if (res.data.code == 0) {
                //list 为从后端导入的数据
                //todo:data的类型问题
                this.tableData = res.data.data;
                console.log("final type message: ", this.tableData);
              } else {
                this.$message.error("读取类型信息失败");
              }
            })
          } else {
            this.$message.error("请选择搜索指标");
          }
        } else {
          this.$message.error("请输入搜索内容");
        }
      }

    },

    //医学影像编辑操作
    handleEditImg(index, row) {
      this.idx = index;
      this.form = row; //把这一行的信息（array）绑定到form上,form也可以使用data具备的子元素
      localStorage.setItem("ms_patient_card_id",this.form.cardId);
      this.$router.replace("/upload");
    },


    // 编辑操作
    handleEdit(index, row) {
      this.idx = index;
      this.form = row; //把这一行的信息（array）绑定到form上,form也可以使用data具备的子元素
      if (localStorage.getItem("ms_type") == 3) {
        this.editVisiblePatient = true;
      } else if (localStorage.getItem("ms_type") == 2) {
        this.editVisibleDoctor = true;
      } else if (localStorage.getItem("ms_type") == 1) {
        this.editVisible = true;
      }
    },
    // 保存编辑
    saveEdit() {
      if (localStorage.getItem("ms_type") == 3) {
        this.editVisiblePatient = false;
      } else if (localStorage.getItem("ms_type") == 2) {
        this.editVisibleDoctor = false;
      } else if (localStorage.getItem("ms_type") == 1) {
        this.editVisible = false;
      }
      console.log("saveEdit form",this.form);
      updatePatientInfo(this.form).then(res => {
        console.log("res.data",res.data);
        console.log("res.data.data", res.data.data);
        if (res.data.code == 0) {
          console.log("final type message: ", this.tableData);
          this.$message.success("修改成功")
        } else {
          this.getData();
          this.$message.error("修改失败");
        }
      })
    },


    // 删除操作
    handleDelete(index, row) {

      if (localStorage.getItem("ms_type") == 3) {
        this.$message.error("没有权限")
      } else if (localStorage.getItem("ms_type") == 1 || localStorage.getItem("ms_type") == 2) {
        // 二次确认删除
        this.$confirm("确定要删除吗？", "提示", {
          type: "warning"
        })
            .then(() => {
              this.tableData.splice(index, 1);
              deletePatientInfo(row.cardId).then(res => {
                console.log("res.data.data", res.data.data);
                if (res.data.code == 0) {
                  //console.log("final type message: ", this.tableData);
                  this.$message.success("删除成功")

                } else {
                  this.$message.error("删除失败");
                }
              })
            })
            .catch(() => {
            });
      }

    },

    // 多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log("handleSelectionChange", val);
    },
    delAllSelection() {

      if (localStorage.getItem("ms_type") == 3) {
        this.$message.error("没有权限")
      } else if (localStorage.getItem("ms_type") == 1 || localStorage.getItem("ms_type") == 2) {
        const length = this.multipleSelection.length;
        let str = "";
        this.delList = this.delList.concat(this.multipleSelection);
        for (let i = 0; i < length; i++) {
          str += this.multipleSelection[i].name + " ";
        }
        this.$message.error(`删除了${str}`);

        deletePatientInfoList(this.multipleSelection).then(res => {
          console.log("res", res);
          console.log("res.data", res.data);
          console.log("res.data.code", res.data.code);
          if (res.data.code == 0) {
            this.$message.success("删除成功")
            this.getData();
          } else {
            this.$message.error("删除失败");
          }
        })
        this.multipleSelection = [];
      }
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #ee0000;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
