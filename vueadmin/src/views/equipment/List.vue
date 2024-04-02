<template>
  <div>
    <el-form :inline="true">

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

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:equipment:add')">新增器材</el-button>
      </el-form-item>
      <!--
      <el-form-item>
        <el-popconfirm title="这是确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatus" v-if="hasAuth('sys:equipment:delete')">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
      -->
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange">
<!--
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          label="器材id"
          width="120"
          prop="id">
      </el-table-column>
        -->
      <el-table-column
          label="器材名称"
          width="120"
          prop="name">
      </el-table-column>

      <el-table-column
          prop="classification"
          label="器材类别"
          width="100">
      </el-table-column>

      <el-table-column
          prop="total"
          label="器材总数">
      </el-table-column>
      <el-table-column
          prop="money"
          label="器材金额(元/每个每天)">
      </el-table-column>

      <el-table-column
          prop="status"
          label="器材状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="success">可租用</el-tag>
          <el-tag size="small" v-else type="danger">不可租用</el-tag>
        </template>

      </el-table-column>

      <el-table-column
          prop="created"
          width="200"
          label="创建时间"
      >
      </el-table-column>

      <el-table-column
          prop="icon"
          width="260px"
          label="操作">

        <template slot-scope="scope">

          <el-button type="text" v-if="hasAuth('sys:equipment:update')" @click="editHandle(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical" v-if="hasAuth('sys:equipment:update')"></el-divider>

          <template>
            <el-popconfirm title="确定删除吗？" v-if="hasAuth('sys:equipment:delete')" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>

    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="器材名称" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="器材类别" prop="classification" label-width="100px">
          <el-input v-model="editForm.classification" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="器材总数" prop="total" label-width="100px">
          <el-input v-model="editForm.total" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="器材金额" prop="money" label-width="100px">
          <el-input v-model="editForm.money" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="器材状态"  prop="status" label-width="100px">
          <el-radio-group v-model="editForm.status">
            <el-radio :label="0">可租用</el-radio>
            <el-radio :label="1">不可租用</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "List",
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
      editFormRules: {
        name: [
          {required: true, message: '请输入器材名称', trigger: 'blur'}
        ],
        classification: [
          {required: true, message: '请输入器材类别', trigger: 'blur'}
        ],
        total: [
          {required: true, message: '请输入器材总数', trigger: 'blur'}
        ],
        money: [
          {required: true, message: '请输入器材金额', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请选择器材状态', trigger: 'blur'}
        ]
      },
      multipleSelection: [],
      roleDialogFormVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getEquipmentList();
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
    /*
    handleSelectionChange(val) {
      console.log("勾选")
      console.log(val)
      this.multipleSelection = val;
      this.delBtlStatus = val.length == 0
    },

     */

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getEquipmentList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getEquipmentList()
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },
    handleClose() {
      this.resetForm('editForm')
    },

    getEquipmentList() {
      this.$axios.get("/sys/equipment/list", {
        params: {
          name: this.searchForm.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].created = this.dateFormat(this.tableData[i].created);
        }
        this.size = res.data.data.pageData.size
        this.current = res.data.data.pageData.current
        this.total = res.data.data.pageData.total
      })
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/equipment/' + (this.editForm.id?'update' : 'add'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作完成',
                  type: 'success',
                  onClose:() => {
                    this.getEquipmentList()
                  }
                });
                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$axios.get('/sys/equipment/info/' + id).then(res => {
        this.editForm = res.data.data.info
        this.dialogVisible = true
      })
    },
    delHandle(id) {
      let ids = []
      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }
      console.log(ids)
      this.$axios.post("/sys/equipment/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '操作完成',
          type: 'success',
          onClose:() => {
            this.getEquipmentList()
          }
        });
      })
    }
  }
}
</script>

<style scoped>

</style>
