<template>
  <div>
    <el-select v-model="value" @change="changeOtherList">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
      </el-option>
    </el-select>

    <el-table v-if="this.value === '1'" ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%; margin-top: 10px;" border stripe>

      <el-table-column label="租用器材" prop="equipmentName">
      </el-table-column>

      <el-table-column prop="status" label="租用状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="info">待审批</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 1" type="success">通过</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 2" type="danger">驳回</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 3">归还</el-tag>
          <el-tag size="small" v-else type="success">归还成功</el-tag>
          <el-tag size="small" v-if="scope.row.repairsStatus === 0">报修中...</el-tag>
        </template>

      </el-table-column>
      <el-table-column prop="starttime" label="租用开始时间">
      </el-table-column>
      <el-table-column prop="starttime" label="租用结束时间">
      </el-table-column>
      <el-table-column prop="icon" width="300" label="操作">

        <template slot-scope="scope">
          <el-button type="text" v-if="scope.row.status === 1" @click="getBackInfo(scope.row.id)">归还</el-button>
          <el-divider v-if="scope.row.status === 1 && (scope.row.repairsStatus === 1 || scope.row.repairsStatus === null)" direction="vertical"></el-divider>
          <el-button type="text" v-if="(scope.row.status === 1 || scope.row.status === 3 || scope.row.status === 4) && (scope.row.repairsStatus === 1 || scope.row.repairsStatus === null)" @click="repairs(scope.row.id,scope.row.userid)">报修</el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-table v-if="this.value === '2'" ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%; margin-top: 10px;" border stripe>

      <el-table-column label="竞赛名称" prop="competitionName">
      </el-table-column>

      <el-table-column prop="status" label="参赛状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="success">已报名</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 1" type="danger">取消报名</el-tag>
        </template>

      </el-table-column>
      <el-table-column prop="created" label="参赛时间">
      </el-table-column>
      <el-table-column prop="icon" width="300" label="操作">
        <template slot-scope="scope">
          <div v-if="new Date() < scope.row.competitionStartTime">
            <el-button type="text" v-if="scope.row.status === 0" @click="cancel(scope.row.competitionid)">取消报名</el-button>
            <el-button type="text" v-else @click="apply(scope.row.competitionid)">报名</el-button>
          </div>
        </template>
      </el-table-column>

    </el-table>

    <el-table v-if="this.value === '3'" ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%; margin-top: 10px;" border stripe>

      <el-table-column label="预约编号" width="80" prop="id">
      </el-table-column>

      <el-table-column prop="placeName" label="预约场地" width="150">
      </el-table-column>

      <el-table-column prop="orderdate" label="预约日期">
      </el-table-column>

      <el-table-column prop="orderstart" label="预约开始时间(点)">
      </el-table-column>

      <el-table-column prop="orderend" label="预约结束时间(点)">
      </el-table-column>

      <el-table-column prop="price" label="预约订单价格">
      </el-table-column>

      <el-table-column prop="status" label="预约状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="success">已预约</el-tag>
          <el-tag size="small" v-else type="danger">取消预约</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="created" width="200" label="预约时间">
      </el-table-column>

      <el-table-column prop="icon" width="250" label="操作">
        <template slot-scope="scope">
          <div v-if="new Date() < new Date(scope.row.orderdate)">
            <el-button type="text" v-if="scope.row.status === 0" @click="cancelAppointment(scope.row.id)">取消预约</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper" style="margin-top: 10px;" :page-sizes="[10, 20, 50, 100]" :current-page="current" :page-size="size" :total="total">
    </el-pagination>
    <!--新增对话框-->
    <el-dialog title="归还" :visible.sync="backFlag" width="600px" :before-close="handleClose">

      <el-form :model="editForm" ref="editForm">
        <el-form-item label="器材名称" label-width="100px">
          <el-input v-model="editForm.equipmentName" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="租用数量" label-width="100px">
          <el-input v-model="editForm.number" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="租用开始时间" label-width="100px">
          <el-input v-model="editForm.starttime" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="租用结束时间" label-width="100px">
          <el-input v-model="editForm.endtime" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="需支付总金额" label-width="100px">
          <el-input v-model="editForm.totalmoney" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>

        <!-- <el-form-item label="二维码" label-width="100px">
          <el-image :src="require('@/assets/erweima.jpg')" style="height: 300px;"></el-image>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="back()">归 还</el-button>
      </div>
    </el-dialog>

    <el-dialog title="报修" :visible.sync="repairsFlag" width="600px" :before-close="handleClose">

      <el-form :model="repairsForm" ref="repairsForm">
        <el-form-item label="报修原因" label-width="100px">
          <el-input type="textarea" :rows="5" placeholder="请输入报修原因" v-model="repairsForm.repairReason" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm()">取 消</el-button>
        <el-button type="primary" @click="goRepairs()">报 修</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "order",
  data () {
    return {
      total: 0,
      size: 10,
      current: 1,
      editForm: {},
      tableData: [],
      options: [{
        value: '1',
        label: '器材'
      }, {
        value: '2',
        label: '赛事'
      }, {
        value: '3',
        label: '场地'
      }],
      value: '1',
      backFlag: false,
      repairsForm: {},
      repairsFlag: false,
      userId: 0,
      applyForm: {}
    }
  },
  created () {
    this.userId = localStorage.getItem("userId");
    this.getOrderList();
  },
  methods: {
    cancel (competitionId) {
      this.$confirm('此操作将取消报名该比赛, 是否继续?', '取消报名', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/user/apply/cancel/" + competitionId + "/" + this.userId).then(res => {
          this.$message({
            type: 'success',
            message: '取消报名成功!',
            onClose: () => {
              this.value = '2';
              this.getOrderList();
            }
          });
        })
      })
    },
    apply (competitionId) {
      this.applyForm.competitionid = competitionId;
      this.applyForm.userid = this.userId;
      this.$confirm('此操作将报名该比赛, 是否继续?', '报名', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/user/apply/save", this.applyForm).then(res => {
          this.$message({
            type: 'success',
            message: '报名成功!',
            onClose: () => {
              this.value = '2';
              this.getOrderList();
            }
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消报名'
        });
      });
    },
    cancelAppointment (appointmentId) {
      this.$confirm('此操作将取消预约该时间段的场地, 是否继续?', '取消预约', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/appointment/cancel/" + appointmentId).then(res => {
          this.$message({
            type: 'success',
            message: '取消预约成功!',
            onClose: () => {
              this.value = '3';
              this.getOrderList();
            }
          });
        })
      })
    },
    changeOtherList (id) {
      this.value = id;
      this.getOrderList();
    },
    repairs (id, userId) {
      this.repairsFlag = true;
      this.repairsForm.borrowid = id;
      this.repairsForm.userid = userId;
    },
    goRepairs () {
      this.$axios.post("/user/repairs/save", this.repairsForm).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，报修成功',
          type: 'success',
          onClose: () => {
            this.value = '1';
            this.getOrderList()
          }
        });
        this.repairsFlag = false;
      })
    },
    dateFormat (time) {
      let date = new Date(time);
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      return (year + "-" + month + "-" + day);
    },

    handleSizeChange (val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getOrderList()
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getOrderList()
    },

    resetForm () {
      this.backFlag = false;
      this.repairsFlag = false;
      this.editForm = {}
    },
    handleClose () {
      this.resetForm()
    },

    getOrderList () {
      let url = "";
      if (this.value === '1') {
        url = "/borrow/getBorrowOrderByUser/" + this.userId;
      } else if (this.value === '2') {
        url = "/user/apply/getApplyOrderByUser/" + this.userId;
      } else {
        url = "/appointment/getAppointmentOrderByUser/" + this.userId;
      }
      this.$axios.get(url, {
        params: {
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].starttime) {
            this.tableData[i].starttime = this.dateFormat(this.tableData[i].starttime);
          }
          if (this.tableData[i].endtime) {
            this.tableData[i].endtime = this.dateFormat(this.tableData[i].endtime);
          }
          if (this.tableData[i].created) {
            this.tableData[i].created = this.dateFormat(this.tableData[i].created);
          }
          if (this.tableData[i].orderdate) {
            this.tableData[i].orderdate = this.dateFormat(this.tableData[i].orderdate);
          }
        }
        this.size = res.data.data.pageData.size
        this.current = res.data.data.pageData.current
        this.total = res.data.data.pageData.total
      })
    },

    back () {
      this.$axios.post('/borrow/back', this.editForm)
        .then(res => {
          this.$message({
            showClose: true,
            message: '恭喜你，归还成功',
            type: 'success',
            onClose: () => {
              this.value = '1';
              this.getOrderList()
            }
          });
          this.backFlag = false;
        })
    },
    getBackInfo (id) {
      this.$axios.get('/borrow/getBackInfo/' + id).then(res => {
        this.editForm = res.data.data.backInfo;
        this.editForm.starttime = this.dateFormat(this.editForm.starttime);
        this.editForm.endtime = this.dateFormat(this.editForm.endtime);
        this.backFlag = true;
        if (new Date().getTime() > new Date(this.editForm.endtime)) {
          this.editForm.totalmoney = this.editForm.totalmoney * 2;
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
