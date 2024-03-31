<template>
  <div>
    <el-form :inline="true">
      <!--
      <el-form-item>
        <el-input
            v-model="searchForm.name"
            placeholder="器材名称"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getEquipmentList">搜索</el-button>
      </el-form-item>
      -->
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe>

      <el-table-column
          label="器材名称"
          prop="name">
      </el-table-column>

      <el-table-column
          prop="classification"
          label="器材类别">
      </el-table-column>

      <el-table-column
          prop="surplus"
          label="器材余量">
      </el-table-column>

      <el-table-column
          prop="money"
          label="器材金额(元/每个每天)">
      </el-table-column>
<!--
      <el-table-column
          prop="icon"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)">租用</el-button>
        </template>
      </el-table-column>
-->
    </el-table>

    <el-pagination
        @size-change="handleSizeChange" style="margin-top: 10px;"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>

    <!--新增对话框
    <el-dialog
        title="租用"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm">
        <el-form-item label="租用数量" label-width="100px">
          <el-input-number v-model="editForm.number" :min="1" :max="this.maxNum"></el-input-number>
        </el-form-item>
        <el-form-item label="租用开始时间" label-width="100px">
          <el-date-picker v-model="editForm.starttime" type="date" placeholder="选择租用开始时间" :picker-options="pickerOptions"></el-date-picker>
        </el-form-item>
        <el-form-item label="租用结束时间" label-width="100px">
          <el-date-picker v-model="editForm.endtime" type="date" placeholder="选择租用开始时间" :picker-options="pickerOptions"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm()">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </div>
    </el-dialog>
    -->
  </div>
</template>

<script>
export default {
  name: "equipment",
  data() {
    return {
      searchForm: {},
      delBtlStatus: true,
      total: 0,
      size: 10,
      current: 1,
      dialogVisible: false,
      editForm: {
        number: 1
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        }
      },
      tableData: [],
      maxNum: 1
    }
  },
  created() {
    this.getEquipmentList();
  },
  methods: {
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
      this.size = val
      this.getEquipmentList()
    },
    handleCurrentChange(val) {
      this.current = val
      this.getEquipmentList()
    },

    resetForm() {
      this.editForm = {};
      this.dialogVisible = false;
      this.editForm.number = 1;
    },
    handleClose() {
      this.editForm = {};
      this.editForm.number = 1;
      this.dialogVisible = false;
    },

    getEquipmentList() {
      this.$axios.get("/equipment/list", {
        params: {
          competitionName: this.searchForm.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records;
        this.size = res.data.data.pageData.size
        this.current = res.data.data.pageData.current
        this.total = res.data.data.pageData.total
      })
    },
    submitForm() {
      if (this.editForm.starttime.getTime() > this.editForm.endtime.getTime()){
        this.$message({
          showClose: true,
          message: '时间错误',
          type: 'error'
        });
        return;
      }
      this.$axios.post('/borrow/save',this.editForm).then(res => {
        this.$message({
          showClose: true,
          message: '操作完成,请等待管理员审核',
          type: 'success',
          onClose:() => {
            this.getEquipmentList()
          }
        });
        this.editForm = {}
        this.editForm.number = 1
        this.dialogVisible = false
      })
    },
    editHandle(id) {
      this.dialogVisible = true;
      this.maxNum = this.tableData[id - 1].surplus;
      this.editForm.equipmentid = this.tableData[id - 1].id;
      this.editForm.userid = localStorage.getItem("userId");
    }
  }
}
</script>

<style scoped>

</style>
