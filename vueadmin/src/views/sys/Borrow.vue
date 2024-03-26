<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.username"
            placeholder="租借用户名"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getBorrowList">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe>

      <el-table-column
          label="租借用户名"
          width="110"
          prop="username">
      </el-table-column>

      <el-table-column
          prop="equipmentName"
          label="租借器材"
          width="110">
      </el-table-column>

      <el-table-column
          prop="number"
          label="数量"
          width="110">
      </el-table-column>

      <el-table-column
          prop="totalmoney"
          label="租用总金额"
          width="110">
      </el-table-column>

      <el-table-column
          prop="status"
          label="租借状态"
          width="220">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="info">待审批</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 1" type="success">通过</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 2" type="danger">驳回</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 3">归还</el-tag>
          <el-tag size="small" v-else type="success">归还成功</el-tag>
          <el-tag size="small" v-if="scope.row.repairsStatus === 0">待修复</el-tag>
        </template>

      </el-table-column>
      <el-table-column
          prop="starttime"
          width="200"
          label="租用开始时间"
      >
      </el-table-column>
      <el-table-column
          prop="endtime"
          width="200"
          label="租用结束时间"
      >
      </el-table-column>
      <el-table-column
          prop="icon"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" v-if="hasAuth('sys:borrow:approve') && scope.row.status === 0" @click="editHandle(scope.row.id)">审批</el-button>
          <el-button type="text" v-if="hasAuth('sys:borrow:approve') && scope.row.status === 3" @click="back(scope.row.id)">回收</el-button>
          <el-divider v-if="scope.row.status === 3" direction="vertical"></el-divider>
          <el-button type="text" v-if="hasAuth('sys:borrow:approve') && scope.row.status === 3" @click="bad(scope.row.id,scope.row.userid)">损坏</el-button>
          <el-divider v-if="scope.row.repairsStatus === 0 && scope.row.status === 3" direction="vertical"></el-divider>
          <el-button type="text" v-if="hasAuth('sys:borrow:approve') && scope.row.repairsStatus === 0" @click="repair(scope.row.id)">修复</el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog
        title="器材审批"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm">
        <el-form-item label="租用数量" label-width="100px">
          <el-input-number v-model="editForm.number" disabled="true"></el-input-number>
        </el-form-item>
        <el-form-item label="租用开始时间" label-width="100px">
          <el-date-picker v-model="editForm.starttime" type="date" disabled="true"></el-date-picker>
        </el-form-item>
        <el-form-item label="租用结束时间" label-width="100px">
          <el-date-picker v-model="editForm.endtime" type="date" disabled="true"></el-date-picker>
        </el-form-item>
        <el-form-item label="操作" label-width="100px">
          <el-radio-group v-model="editForm.status">
            <el-radio :label="1">通过</el-radio>
            <el-radio :label="2">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm()">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="损坏"
        :visible.sync="badFlag"
        width="600px"
        :before-close="handleClose">

      <el-form :model="badForm">
        <el-form-item label="赔偿金额" label-width="100px">
          <el-input v-model="badForm.price" ></el-input>
        </el-form-item>
        <el-form-item label="赔偿原因" label-width="100px">
          <el-input v-model="badForm.reason"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm()">取 消</el-button>
        <el-button type="primary" @click="submitBad()">确 定</el-button>
      </div>
    </el-dialog>

    <el-pagination style="margin-top: 10px;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "Borrow",
  data() {
    return {
      searchForm: {},
      delBtlStatus: true,
      total: 0,
      size: 10,
      current: 1,
      dialogVisible: false,
      editForm: {},
      tableData: [],
      badFlag: false,
      badForm: {
        reason: '损坏器材'
      }
    }
  },
  created() {
    this.getBorrowList();
  },
  methods: {
    repair(id){
      this.$confirm('此操作已修复该器材, 是否继续?', '修复', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/sys/borrow/repair/" + id).then(res => {
          this.$message({
            type: 'success',
            message: '修复成功!',
            onClose:() => {
              this.getBorrowList()
            }
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消修复'
        });
      });
    },
    bad(id,userId){
      this.badForm.borrowid = id;
      this.badForm.userid = userId;
      this.badFlag = true;
    },
    submitBad(){
      this.$axios.post("/sys/compensate/save",this.badForm).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getBorrowList()
          }
        });
        this.badFlag = false
      })
    },
    back(id){
      this.$confirm('此操作将回收该器材, 是否继续?', '回收', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/sys/borrow/back/" + id).then(res => {
          this.$message({
            type: 'success',
            message: '回收成功!',
            onClose:() => {
              this.getBorrowList()
            }
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消回收'
        });
      });
    },
    dateFormat(time) {
      let date = new Date(time);
      let year = date.getFullYear();
      let month =
          date.getMonth() + 1 < 10
              ? "0" + (date.getMonth() + 1)
              : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      return (year + "-" + month + "-" + day);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getBorrowList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getBorrowList()
    },

    resetForm() {
      this.dialogVisible = false;
      this.badFlag = false;
      this.editForm = {}
      this.badForm = {}
      this.badForm.reason = '损坏器材'
    },
    handleClose() {
      this.resetForm();
    },

    getBorrowList() {
      this.$axios.get("/sys/borrow/list", {
        params: {
          username: this.searchForm.username,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].starttime = this.dateFormat(this.tableData[i].starttime);
          this.tableData[i].endtime = this.dateFormat(this.tableData[i].endtime);
        }
        this.size = res.data.data.pageData.size
        this.current = res.data.data.pageData.current
        this.total = res.data.data.pageData.total
      })
    },

    submitForm() {
      if (this.editForm.status === 0){
        this.$message({
          showClose: true,
          message: '请选择审批操作',
          type: 'error'
        });
        return;
      }
      this.$axios.post('/sys/borrow/passOrNotPass', this.editForm)
          .then(res => {
            this.$message({
              showClose: true,
              message: '恭喜你，操作成功',
              type: 'success',
              onClose:() => {
                this.getBorrowList()
              }
            });
            this.dialogVisible = false
          })
    },
    editHandle(id) {
      this.$axios.get('/sys/borrow/info/' + id).then(res => {
        this.editForm = res.data.data.info
        this.dialogVisible = true
      })
    },
  }
}
</script>

<style scoped>

</style>
