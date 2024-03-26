<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.username"
            placeholder="预约用户名"
            clearable>
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getAppointmentList">搜索</el-button>
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
          label="预约编号"
          width="80"
          prop="id">
      </el-table-column>

      <el-table-column
          prop="username"
          label="预约用户名"
          width="150">
      </el-table-column>

      <el-table-column
          prop="placeName"
          label="预约场地"
          width="150">
      </el-table-column>

      <el-table-column
          prop="orderdate"
          label="预约日期">
      </el-table-column>

      <el-table-column
          prop="orderstart"
          label="预约开始时间(点)">
      </el-table-column>

      <el-table-column
          prop="orderend"
          label="预约结束时间(点)">
      </el-table-column>

      <el-table-column
          prop="price"
          label="预约订单价格">
      </el-table-column>

      <el-table-column
          prop="status"
          label="预约状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="success">已预约</el-tag>
          <el-tag size="small" v-else type="danger">取消预约</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="created"
          width="200"
          label="预约时间">
      </el-table-column>
    </el-table>

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
  name: "Appointment",
  data() {
    return {
      searchForm: {},
      delBtlStatus: true,
      total: 0,
      size: 10,
      current: 1,
      tableData: []
    }
  },
  created() {
    this.getAppointmentList();
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
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
      this.getAppointmentList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getAppointmentList()
    },
    getAppointmentList() {
      this.$axios.get("/sys/appointment/list", {
        params: {
          username: this.searchForm.username,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].orderdate = this.dateFormat(this.tableData[i].orderdate);
          this.tableData[i].created = this.dateFormat(this.tableData[i].created);
        }
        this.size = res.data.data.pageData.size
        this.current = res.data.data.pageData.current
        this.total = res.data.data.pageData.total
      })
    }
  }
}
</script>

<style scoped>

</style>
