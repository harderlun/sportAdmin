<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getRoleList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:role:save')">新增</el-button>
      </el-form-item>

      <el-popconfirm title="确定批量删除吗？" @confirm="delHandle(null)">
        <el-button type="danger" slot="reference" :disabled="delBtlStatus" v-if="hasAuth('sys:role:delete')">批量删除</el-button>
      </el-popconfirm>
    </el-form>

    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          prop="name"
          label="名称"
          width="120">
      </el-table-column>

      <el-table-column
          prop="code"
          label="唯一编码"
          show-overflow-tooltip>
      </el-table-column>

      <el-table-column
          prop="remark"
          label="描述"
          show-overflow-tooltip>
      </el-table-column>

      <el-table-column
          prop="status"
          label="状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 1" type="success">正常</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 0" type="danger">禁用</el-tag>
        </template>

      </el-table-column>
      <el-table-column
          prop="icon"
          label="操作">
        <template slot-scope="scope">

          <el-button type="text" @click="permHandle(scope.row.id)" v-if="hasAuth('sys:role:perm')">分配权限</el-button>
          <el-divider direction="vertical" v-if="hasAuth('sys:role:perm')"></el-divider>

          <el-button type="text" @click="editHandle(scope.row.id)" v-if="hasAuth('sys:role:update')">编辑</el-button>
          <el-divider direction="vertical" v-if="hasAuth('sys:role:perm')"></el-divider>
          <template>
            <el-popconfirm title="确定删除该数据吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference" v-if="hasAuth('sys:role:delete')">删除</el-button>
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
        :page-size="size"
        :current-page="current"
        :total="total">
    </el-pagination>

    <!--新增对话框-->
    <el-dialog
        title="新增角色"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">

        <el-form-item label="角色名称" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="唯一编码" prop="code" label-width="100px">
          <el-input v-model="editForm.code" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" prop="remark" label-width="100px">
          <el-input v-model="editForm.remark" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="status" label-width="100px">
          <el-radio-group v-model="editForm.status">
            <el-radio :label=0>禁用</el-radio>
            <el-radio :label=1>正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">提交</el-button>
          <el-button @click="resetForm('editForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

    <el-dialog
        title="分配权限"
        :visible.sync="permDialogVisible"
        width="600px">
      <el-form :model="permForm">
        <el-tree
            :data="permTreeData"
            show-checkbox
            ref="permTree"
            :default-expand-all="true"
            node-key="id"
            :props="defaultProps">
        </el-tree>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="permDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitPermFormHandle('permForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Role",
  data(){
    return{
      searchForm: {

      },
      size: 10,
      current: 1,
      total: 0,
      delBtlStatus: true,
      tableData: [],
      editFormRules: {
        name: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入唯一编码', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },
      dialogVisible: false,
      permDialogVisible: false,
      editForm: {},
      multipleSelection: [],
      permForm: {},
      permTreeData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getRoleList();
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.delBtlStatus = val.length == 0;
    },
    handleSizeChange(val) {
      this.size = val;
      this.getRoleList();
    },
    handleCurrentChange(val) {
      this.current = val;
      this.getRoleList();
    },
    handleClose(){
      this.resetForm('editForm')
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false;
      this.editForm = {};
    },
    getRoleList(){
      this.$axios.get("/sys/role/list",{
        params: {
          name: this.searchForm.name,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records;
        this.size = res.data.data.pageData.size;
        this.current = res.data.data.pageData.current;
        this.total = res.data.data.pageData.total;
      })
    },
    submitForm(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/role/' + (this.editForm.id ? 'update' : 'save'),this.editForm).then(res => {
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success',
              onClose: () => {
                this.getRoleList();
              }
            });
            this.dialogVisible = false;
            this.editForm = {};
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id){
      this.$axios.get('/sys/role/info/' + id).then(res => {
        this.editForm = res.data.data.info;
        this.dialogVisible = true;
      })
    },
    delHandle(id){
      let ids = [];
      if (id){
        ids.push(id);
      }else{
        this.multipleSelection.forEach(row => {
          ids.push(row.id);
        })
      }
      this.$axios.post("/sys/role/delete",ids).then(res => {
        this.$message({
          showClose: true,
          message: '操作成功',
          type: 'success',
          onClose: () => {
            this.getRoleList();
          }
        });
      })
    },
    permHandle(id){
      this.$axios.get('/sys/menu/list').then(res => {
        this.permTreeData = res.data.data.menuList;
        this.permDialogVisible = true;
        this.$axios.get("/sys/role/info/" + id).then(res => {
          this.$refs.permTree.setCheckedKeys(res.data.data.info.menuIds);
          this.permForm = res.data.data.info;
        })
      })
    },
    submitPermFormHandle(formName){
      let menuIds = this.$refs.permTree.getCheckedKeys();
      if (menuIds.indexOf(1) == -1 && (menuIds.indexOf(2) > -1 || menuIds.indexOf(3) > -1 || menuIds.indexOf(4) > -1 || menuIds.indexOf(21) > -1)) menuIds.push(1);
      if (menuIds.indexOf(5) == -1 && (menuIds.indexOf(6) > -1 || menuIds.indexOf(32) > -1)) menuIds.push(5);
      if (menuIds.indexOf(36) == -1 && menuIds.indexOf(38) > -1) menuIds.push(36);
      if (menuIds.indexOf(37) == -1 && (menuIds.indexOf(25) > -1 || menuIds.indexOf(42) > -1)) menuIds.push(37);
      console.log(menuIds);
      this.$axios.post("/sys/role/perm/" + this.permForm.id,menuIds).then(res => {
        this.$message({
          showClose: true,
          message: '操作成功',
          type: 'success',
          onClose: () => {
            this.getRoleList();
          }
        });
        this.permDialogVisible = false;
      })
    }
  }
}
</script>

<style scoped>
.el-pagination{
  float: right;
  margin-top: 22px;
}
</style>
