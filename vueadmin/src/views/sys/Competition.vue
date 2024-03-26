<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.competitionName"
            placeholder="赛事名称"
            clearable>
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getCompetitionList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:competition:save')">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="这是确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatus" v-if="hasAuth('sys:competition:delete')">批量删除</el-button>
        </el-popconfirm>
      </el-form-item>
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
          label="赛事名称"
          width="120"
          prop="name">
      </el-table-column>

      <el-table-column
          label="赛事图片"
          width="120"
          prop="url">
        <template slot-scope="scope">
          <el-image v-if="scope.row.url" :src="scope.row.url"></el-image>
        </template>
      </el-table-column>

      <el-table-column
          prop="username"
          label="赛事创建人">
      </el-table-column>
      <el-table-column
          prop="place"
          label="赛事地点">
      </el-table-column>
      <el-table-column
          prop="starttime"
          label="赛事开始时间"
      ></el-table-column>
      <el-table-column
          prop="endtime"
          label="赛事结束时间"
      ></el-table-column>
      <el-table-column
          prop="status"
          label="赛事状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.status === 0" type="success">发布</el-tag>
          <el-tag size="small" v-else-if="scope.row.status === 1" type="danger">未发布</el-tag>
          <el-tag size="small" v-else type="info">暂存</el-tag>
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
          width="250"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" v-if="hasAuth('sys:competition:update')" @click="editHandle(scope.row.id)">编辑</el-button>
          <el-divider direction="vertical" v-if="hasAuth('sys:competition:update')"></el-divider>

          <template>
            <el-popconfirm title="这是一段内容确定删除吗？" v-if="hasAuth('sys:competition:delete')" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </template>
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
    <!--新增对话框-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm">
        <el-form-item label="赛事名称" prop="name" label-width="120px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="赛事地点" prop="place" label-width="120px">
          <el-input v-model="editForm.place" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="赛事开始时间" label-width="120px">
          <el-date-picker v-model="editForm.starttime" type="datetime" placeholder="选择赛事开始时间" :picker-options="pickerOptions"></el-date-picker>
        </el-form-item>

        <el-form-item label="赛事结束时间" label-width="120px">
          <el-date-picker v-model="editForm.endtime" type="datetime" placeholder="选择赛事开始时间" :picker-options="pickerOptions"></el-date-picker>
        </el-form-item>

        <el-form-item label="赛事介绍"  prop="description" label-width="120px">
          <el-input type="textarea" :rows="7" placeholder="请输入赛事介绍" v-model="editForm.description" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="状态"  prop="status" label-width="120px">
          <el-radio-group v-model="editForm.status">
            <el-radio :label="0">发布</el-radio>
            <el-radio :label="1">未发布</el-radio>
            <el-radio :label="2">暂存</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="赛事图片" label-width="120px">
          <el-upload
              class="avatar-uploader"
              :show-file-list="false"
              action="action"
              :http-request="upload"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <el-image v-if="this.imgUrl" :src="this.imgUrl" class="avatar"></el-image>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
  name: "Competition",
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
          {required: true, message: '请输入赛事名称', trigger: 'blur'}
        ],
        place: [
          {required: true, message: '请输入赛事地点', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入赛事介绍', trigger: 'blur'}
        ]
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        }
      },
      multipleSelection: [],
      imgUrl: ''
    }
  },
  created() {
    this.getCompetitionList();
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
    upload (param) {
      console.log(param);
      const formData = new FormData()
      formData.append('file', param.file)
      this.$axios.post('/upload/img',formData,{headers: {'Content-Type': 'multipart/form-data'}}).then(res => {
        this.imgUrl = res.data.data.fileUrl;
        console.log(this.editForm.url);
      })
    },
    handleAvatarSuccess(res, file) {
      console.log(res);
      console.log(file);
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
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
    handleSelectionChange(val) {
      console.log("勾选")
      console.log(val)
      this.multipleSelection = val;
      this.delBtlStatus = val.length == 0
    },

    handleSizeChange(val) {
      this.size = val
      this.getCompetitionList()
    },
    handleCurrentChange(val) {
      this.current = val
      this.getCompetitionList()
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.imgUrl = ''
      this.editForm = {}
    },
    handleClose() {
      this.imgUrl = '';
      this.resetForm('editForm')
    },

    getCompetitionList() {
      this.$axios.get("/sys/competition/list", {
        params: {
          competitionName: this.searchForm.competitionName,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.pageData.records
        for (let i = 0; i < this.tableData.length; i++) {
          this.tableData[i].starttime = this.dateFormat(this.tableData[i].starttime);
          this.tableData[i].endtime = this.dateFormat(this.tableData[i].endtime);
          this.tableData[i].created = this.dateFormat(this.tableData[i].created);
        }
        this.size = res.data.data.pageData.size
        this.current = res.data.data.pageData.current
        this.total = res.data.data.pageData.total
      })
    },

    submitForm(formName) {
      if (new Date(this.editForm.starttime) >= new Date(this.editForm.endtime)){
        this.$message({
          showClose: true,
          message: '竞赛时间不规范',
          type: 'error'
        });
        return;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.editForm.userid = localStorage.getItem("userId");
          this.editForm.url = this.imgUrl;
          this.$axios.post('/sys/competition/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose:() => {
                    this.getCompetitionList()
                  }
                });
                this.dialogVisible = false
                this.imgUrl = '';
                this.editForm = {}
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    editHandle(id) {
      this.$axios.get('/sys/competition/info/' + id).then(res => {
        this.editForm = res.data.data.info
        this.dialogVisible = true
        this.imgUrl = this.editForm.url
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
      this.$axios.post("/sys/competition/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getCompetitionList()
          }
        });
      })
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border: 1px dashed #d9d9d9;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
