/*
 Navicat Premium Data Transfer

 Source Server         : 项目
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : yu-auth

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 29/05/2023 10:39:16
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for place_vdstate
-- ----------------------------
DROP TABLE IF EXISTS `place_vdstate`;
CREATE TABLE `place_vdstate`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '场地预约详细编号',
  `placeId` bigint(0) NOT NULL COMMENT '场地编号',
  `vdstateDate` date NOT NULL COMMENT '日期',
  `vdstateSt` char(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '000000000000' COMMENT '分时状态，第i位代表第i~i + 1小时的场地状态，0代表可用，1代表不可用，2代表已经被预约',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_place_placeId`(`placeId`) USING BTREE,
  CONSTRAINT `FK_place_placeId` FOREIGN KEY (`placeId`) REFERENCES `sys_place` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of place_vdstate
-- ----------------------------
INSERT INTO `place_vdstate` VALUES (1, 1, '2023-05-26', '220121200002');
INSERT INTO `place_vdstate` VALUES (2, 1, '2023-05-27', '211200000000');
INSERT INTO `place_vdstate` VALUES (3, 1, '2023-05-28', '000210000000');
INSERT INTO `place_vdstate` VALUES (4, 2, '2023-05-26', '001220000000');
INSERT INTO `place_vdstate` VALUES (5, 2, '2023-05-27', '000210120001');
INSERT INTO `place_vdstate` VALUES (6, 2, '2023-05-28', '000200000000');
INSERT INTO `place_vdstate` VALUES (7, 3, '2023-05-26', '002000000000');
INSERT INTO `place_vdstate` VALUES (8, 3, '2023-05-27', '000000000000');
INSERT INTO `place_vdstate` VALUES (9, 3, '2023-05-28', '000020000000');
INSERT INTO `place_vdstate` VALUES (10, 4, '2023-05-26', '000000000000');
INSERT INTO `place_vdstate` VALUES (11, 4, '2023-05-27', '000000000000');
INSERT INTO `place_vdstate` VALUES (12, 4, '2023-05-28', '000000000000');
INSERT INTO `place_vdstate` VALUES (25, 1, '2023-05-29', '000000000000');
INSERT INTO `place_vdstate` VALUES (26, 2, '2023-05-29', '000010000000');
INSERT INTO `place_vdstate` VALUES (27, 3, '2023-05-29', '000000000000');
INSERT INTO `place_vdstate` VALUES (28, 4, '2023-05-29', '000000000000');
INSERT INTO `place_vdstate` VALUES (29, 1, '2023-05-30', '000020020000');
INSERT INTO `place_vdstate` VALUES (30, 2, '2023-05-30', '000100100000');
INSERT INTO `place_vdstate` VALUES (31, 3, '2023-05-30', '000002000000');
INSERT INTO `place_vdstate` VALUES (32, 4, '2023-05-30', '000000000000');
INSERT INTO `place_vdstate` VALUES (33, 1, '2023-05-31', '000000000000');
INSERT INTO `place_vdstate` VALUES (34, 2, '2023-05-31', '000000000000');
INSERT INTO `place_vdstate` VALUES (35, 3, '2023-05-31', '000000000000');
INSERT INTO `place_vdstate` VALUES (36, 4, '2023-05-31', '000000000000');

-- ----------------------------
-- Table structure for sys_compensate
-- ----------------------------
DROP TABLE IF EXISTS `sys_compensate`;
CREATE TABLE `sys_compensate`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '赔偿id',
  `borrowId` bigint(0) NOT NULL COMMENT '租借id',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赔偿原因',
  `price` bigint(0) NOT NULL COMMENT '赔偿金额',
  `status` int(0) NOT NULL COMMENT '赔偿状态，0-未支付，1-支付',
  `userId` bigint(0) NOT NULL COMMENT '用户id',
  `created` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_borrow_borrowId`(`borrowId`) USING BTREE,
  INDEX `FK_user_id`(`userId`) USING BTREE,
  CONSTRAINT `FK_borrow_borrowId` FOREIGN KEY (`borrowId`) REFERENCES `user_borrow` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_user_id` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1660929621071814663 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_compensate
-- ----------------------------
INSERT INTO `sys_compensate` VALUES (1, 4, '损坏器材', 50, 1, 1, '2023-05-23 00:00:00');
INSERT INTO `sys_compensate` VALUES (2, 18, '损坏器材', 60, 1, 2, '2023-05-26 00:00:00');
INSERT INTO `sys_compensate` VALUES (3, 24, '损坏器材', 50, 1, 2, '2023-05-27 00:00:00');
INSERT INTO `sys_compensate` VALUES (4, 90, '损坏器材', 50, 1, 2, '2023-05-28 17:08:29');
INSERT INTO `sys_compensate` VALUES (5, 82, '损坏器材', 20, 1, 2, '2023-05-28 17:13:25');
INSERT INTO `sys_compensate` VALUES (6, 88, '损坏器材', 20, 1, 1, '2023-05-28 19:30:26');
INSERT INTO `sys_compensate` VALUES (7, 81, '损坏器材', 20, 1, 2, '2023-05-28 19:32:28');
INSERT INTO `sys_compensate` VALUES (8, 91, '损坏器材', 20, 1, 2, '2023-05-28 23:05:56');
INSERT INTO `sys_compensate` VALUES (9, 92, '损坏器材', 20, 1, 2, '2023-05-29 07:57:56');
INSERT INTO `sys_compensate` VALUES (10, 94, '损坏器材', 20, 1, 2, '2023-05-29 08:03:39');

-- ----------------------------
-- Table structure for sys_competition
-- ----------------------------
DROP TABLE IF EXISTS `sys_competition`;
CREATE TABLE `sys_competition`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '竞赛id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛图片',
  `startTime` datetime(0) NOT NULL COMMENT '竞赛开始时间',
  `endTime` datetime(0) NOT NULL COMMENT '竞赛结束时间',
  `status` int(0) NOT NULL COMMENT '竞赛状态，0-未发布，1-发布，2-暂存',
  `userId` bigint(0) NOT NULL COMMENT '竞赛创建人',
  `created` datetime(0) NOT NULL COMMENT '竞赛创建时间',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛地点',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛介绍',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_competition_userId`(`userId`) USING BTREE,
  CONSTRAINT `FK_competition_userId` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1661035839670296582 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_competition
-- ----------------------------
INSERT INTO `sys_competition` VALUES (1, '全国跳绳联赛', 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/f3b0.jpg', '2023-05-24 12:00:00', '2023-05-27 00:00:00', 0, 1, '2023-05-23 00:00:00', '广东海洋大学', ' 为全面贯彻落实党的十九大和十九届二中、三中、四中、五中全会精神，深入实施创新驱动发展战略，普及科学知识，弘扬科学精神。根据《科技部办公厅关于举办2021年全国科普讲解大赛的通知》(国科办函才〔2021〕361号)，结合新冠疫情防控工作实际，特制定此方案。\n一、活动主题\n       以“百年回望：中国共产党领导体育科技发展”为主题，通过讲解比赛广泛普及体育科学知识、倡导科学健身方法、提高全民体育科学素质，促进体育科技创新发展，让体育科普服务于人民大众，不断提升人民群众的生活质量和健康水平，助力实现体育强国梦。\n二、活动组织\n主办单位：体育总局科教司\n承办单位：武汉体育学院\n三、比赛内容\n全国体育科普讲解比赛分为初赛和决赛。初赛内容为自主命题讲解；决赛内容为自主命题讲解、随机命题讲解和科技常识问答。选手出场时，先进行20秒自我介绍，该环节不作为比赛评分内容。初赛采取视频录制方式开展，不集中比赛。\n自主命题讲解时间为4分钟，由选手自行确定一个熟悉的科普内容进行讲解，可自行设定场景和对象，选题必须切合主题并彰显体育特色，重点围绕主题进行讲解。选手必须借助多媒体等手段辅助进行讲解，提升讲解效果。\n随机命题讲解时间为2分钟，考核选手的随机应变能力和对相关问题的个人见解，候选命题为看图讲解，共有20张图片。选手现场随机抽取确定讲解图片，讲解内容必须与图片内容密切相关，该环节主要考核选手的随机反应能力和发散思维。\n科技常识问答每题限时10秒，主要考察选手的科技素养与知识水平，比赛时由选手随机从《中国公民科学素质基准》题库抽取2道题目进行回答。\n随机命题讲解图片、《中国公民科学素质基准》题库可登陆国赛官方网站下载。\n国赛官方网站地址：http://www.gdsc.cn/qgkpjj2021\n四、赛程安排\n（一）选手推荐阶段\n推荐截止时间：8月6日。\n推荐人员范围：参赛选手为科普工作者、专职及兼职科普讲解人员、科学传播爱好者（职业不限、年龄16周岁以上）等，鼓励广大科技人员参赛。比赛时使用普通话。\n参赛选手需分别录制自我介绍视频和自主命题讲解视频。视频统一采用MP4等通用编码格式，画面比例16：9，全高清1920*1080。在录制自主命题讲解视频时，背景为 PPT 或动画视频等多媒体的全景图像，镜头不可移动，画面应全程连续、不可中断，完整呈现选手讲解的全过程；选手需脱稿讲解，讲解全程自行操作 PPT 或动画视频等多媒体，不可使用提词器等辅助设备，选手不可脱离画面。各单位在报送参赛视频时要切实加强审查，视频录制不符合要求将影响选手进入初赛。\n（二）初赛阶段\n时间：8月上旬（具体时间另行通知）。\n地点：武汉体育学院\n初赛阶段，选手不实地参赛。由专家评委对每位选手的参赛视频进行评分，按得分高低确定名次，得分前20名选手进入决赛。\n（三）决赛阶段.\n时间：8月中旬（具体时间另行通知）。\n地点：待定。\n进入决赛的20名选手需实地参赛，并按抽签顺序依次出场。由专家评委对每位选手进行评分，按得分高低确定名次。决赛前10名选手获“全国体育系统十佳科普讲解员”称号，其余选手获“全国体育系统优秀科普讲解员”称号。\n如受疫情影响或国家、地方联防联控机制有具体要求，将及时对比赛时间、地点及形式等进行调整。\n五、评判规则及比赛监督\n（一）初赛评判规则。\n1.由7名专家评委对每位选手的参赛视频进行评分，按得分高低确定名次。打分采用网络打分的方式，所有评委打分去掉一个最高分和一个最低分后的平均数为选手的评委分数。将选手的评委分数及超时、少时扣分的分数相加，得出该选手的总分数。若遇选手总分数相同则按评委的第二个最高分高低决定名次，若评委的第二个最高分相同则按第三个最高分高低决定名次，以此类推；若遇评委具体打分均相同，则在监督组的监督下抽签决定名次。\n2.评分规则。总分100分，评分保留到小数点后二位，讲解超时由记分员进行扣分记录。评委分别从内容陈述、表达效果、整体形象三方面进行评分。内容必须包含自然科学和技术知识，否则不得分。\n①内容陈述(40分)\n科学准确、重点突出；\n层次清楚、合乎逻辑。\n②表达效果（40分）\n发音标准、吐字清晰；\n通俗易懂、深入浅出。\n③整体形象（20分）\n衣着得体、精神饱满；\n举止大方、自然协调。\n讲解限时4分钟，不足3分钟扣2分，超时10秒后讲解中止扣2分。\n（二）决赛评判规则。\n1.由7名专家评委对选手进行评分，按得分高低确定名次。对自主命题讲解和随机命题讲解进行打分，科技常识测试扣分情况由记分员记录，所有评委综合打分后，去掉最高分和最低分后的平均数为最终得分。打分采用现场打分、亮分和公布成绩的方式。评委不对选手的时间使用情况进行记录，由记分员进行记录。将评委分数、科技常识测试扣分数及超时、少时扣分的分数相加，得出该选手的总分数。若遇选手总分数相同，则按评委的第2个最高分高低决定名次，以此类推；若遇评委具体打分均相同，则在监督组的监督下抽签决定名次。\n2.评分规则。总分100分，评分保留到小数点后二位，讲解超时由记分员进行扣分记录。\n自主命题讲解(70分)。评委分别从内容陈述、表达效果、整体形象三方面进行评分。内容必须包含自然科学和技术知识，否则不得分。\n①内容陈述（30分）\n科学准确、重点突出；\n层次清楚、合乎逻辑。\n②表达效果（30分）\n发音标准、吐字清晰；\n通俗易懂、深入浅出。\n③整体形象（10分）\n衣着得体、精神饱满；\n举止大方、自然协调。\n讲解限时4分钟，不足3分钟扣2分，超时10秒后讲解中止扣2分。\n随机命题讲解(30分)。现场有20个图片供选手选择，选手选取图片后，根据图片内容进行讲解。选手可在20秒准备时间后开始计时讲解。内容必须与图片密切相关，并包含自然科学和技术知识，否则不得分。专家评委将根据以下4个方面进行评分，超时则由记分员进行扣分记录。\n①主题立论一致，合乎逻辑；\n②内容重点突出，寓意深刻；\n③密切联系生活，特色鲜明；\n④讲解思路清晰，语言流畅。\n随机命题讲解限时2分钟，不足1分钟扣2分，超时10秒后讲解中止扣2分。\n科技常识问答环节各题限时10秒，选手每人随机选取2道科技常识问题（从《中国公民科学素质基准》中选取）进行回答，由计分员在监督组的监督下根据答题情况记录选手扣分情况。回答正确不扣分；不回答、回答错误或超时，1题扣1分，2题扣2分。\n（三）比赛监督\n为保证大赛的公开、公平、公正，比赛成立独立监督组对决赛活动进行监督。专家评委和监督组成员名单由科教司另文通知。\n六、相关事宜\n（一）加强组织推动\n请各单位以这次全国体育科普讲解比赛为契机，广泛动员、加强谋划，切实加强体育科普工作，将体育科普工作作为落实“体育强国＂的一项重要内容来推动落实。\n（二）做好比赛保障\n1.请承办单位做好全国体育科普讲解比赛的组织承办工作，严格落实疫情防控有关要求，制定应急预案，确保比赛顺利开展。\n2.请各参赛单位统筹资源大力支持参赛选手的比赛筹备，发挥集体智慧提升选手科普讲解水平，做好道具、视频、服装等保障。参加决赛的选手要切实做好个人防护，参赛期间如有发热、咳嗽等症状应立即报告。\n3.选手参赛均以集中工作形式开展，差旅费由参赛单位按出差有关规定办理。');
INSERT INTO `sys_competition` VALUES (2, '全国国际象棋比赛', 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/5bfa.jpg', '2023-05-25 00:00:00', '2023-05-27 00:00:00', 0, 1, '2023-05-24 00:00:00', '广东海洋大学', '一、主办单位\n中国大学生体育协会\n中国国际象棋协会\n二、执行单位\n中国大学生体育协会棋类分会\n三、承办单位\n江苏省棋类运动协会\n四、协办单位\n南京橘中秘体育文化传播有限公司\n南京扬子晚报云教育科技有限公司\n五、比赛时间及地点\n时间：2019年10月3日—6日\n地点：南京市\n六、竞赛分组及项目\n(一)竞赛分组：\n1.等级组：具有等级称号的棋手(运动健将、一至三级运动员及棋协大师、候补棋协大师);\n2.棋士组：棋协一级棋士(含)以下及无等级称号的棋手。\n3.参赛棋手分两个组别：等级组的棋手不能参加棋士组比赛，一旦发现或被举报，取消该棋手的比赛成绩并通报。\n(二)竞赛项目：个人赛和团体赛。\n七、竞赛办法\n1.比赛执行国家体育总局审定的最新竞赛规则。\n2.采用瑞士制编排，比赛9轮棋，每天3轮。\n3.比赛以个人赛形式决出名次，根据个人成绩计算团体名次。\n4.男女分组比赛，如女子人数不足，视情况男女混合编组。\n八、录取名次与奖励办法\n(一)个人赛前8名颁发成绩证书，前3名颁发奖牌。\n(二)团体录取名次：总团体录取前3名、分组团体录取前8名，颁发奖杯和成绩证书。\n(三)团体名次计算方法：\n1.等级组团体，以最好成绩2男1女(共3人)最好名次计算。\n2.棋士组团体，以最好成绩2男1女(共3人)最好名次计算。\n3.总团体，以各队在等级组男女各一人和棋士组男女各一人(共4人)最好名次计算。\n(四)根据最新《中国国际象棋协会棋士等级称号条例》文件执行，按照每位棋手得分率可申请相应的棋协等级称号。\n(五)每名参赛选手都将获得参赛证书。\n九、参赛单位\n全国各普通高等院校均可以学校为单位报名参赛。\n十、运动员资格\n1.参赛运动员必须是具有中华人民共和国国籍和中华人民共和国居民身份证(第二代)或香港、澳门特别行政区身份证或台湾居民居住证。\n2.按照教育部全国普通高等学校统一招生考试、录取的有关规定，经考生所在地普通高等学校招生委员会(办公室)审核录取，并在“中国高等教育学生信息网”注册的普通全日制在校学生。\n3.思想进步、文化课考核合格、遵守运动员守则、经运动员所在学校医院检查证明身体健康并适宜参加所报项目比赛者。\n十一、注册办法\n1.所有参赛运动员必须以学生所在学校为单位在《中国学生体育竞赛管理系统》(www.nssc.org.cn)中进行网上注册。\n2.参赛运动员所在学校的学校管理员需登录《中国学生体育竞赛管理系统》(www.nssc.org.cn)进行运动员和教练员网上注册。(详见《中国学生体育竞赛管理系统》首页的“系统使用者必看”和“IE浏览器调整”中的相关规定)。\n3.注册时，需按要求填写教练员信息、运动员信息、上传参赛运动员本人的学生证(电子版)、第二代身份证件(电子版)、近期免冠一寸照片(照片需根据网站要求调整尺寸大小)等有关材料。\n4.联系方式：教育部学生体育协会联合秘书处注册工作联系人：孙变丽，李阳，联系电话：010-66093753，010-66093749(周一至周五9:00—16:30)。\n十二、报名办法\n1.参赛单位可任意选派各组别队，运动员人数不限。\n2.每单位可报领队1人、教练员1人。\n3.报名表(见附件1)于2019年9月18日前以邮件形式给承办单位。\n联系人：刘老师\n电话：13915980165 邮箱：379571438@qq.com\n十三、报到\n1.报到时间：2019年10月2日12:00—19:00前。\n2.报到地点：南京(具体地点请见补充通知)\n3.报到时参赛队需携带公章的报名表原件及《自愿参赛责任及风险告知书》。参赛人员出具身份证、学信网学籍证明、保险证明、交由组委会统一查验。\n4.各参赛队报到前须为全体人员办理阶段性保险(含往返赛区途中及比赛期间)，报到时向赛会交验保险单据。未办理者，不能参赛。\n十四、经费\n1.各参赛队往返交通、食宿费各队自理;\n2.大会负责竞赛、宣传、场地、器材等费用;\n3.具体收费详见补充通知。\n十五、未尽事宜，另行通知。\n十六、本规程解释权属于赛事主办单位。');
INSERT INTO `sys_competition` VALUES (3, '全国游泳公开赛', 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/1107.jpg', '2023-05-27 00:00:00', '2023-05-30 00:00:00', 0, 1, '2023-05-24 00:00:00', '广东海洋大学', '一、主办单位\n北京市游泳运动协会、天津市游泳运动协会、河北省游泳协会\n二、承办单位\n北京市社会体育管理中心、北京国家游泳中心有限责任公司、首都体育学院•京津冀体育健身休闲发展协同创新中心\n三、协办单位\n北京水立方游泳俱乐部有限公司、北京体育赛事管理与营销研究基地、北京中意明安科技有限责任公司\n四、支持单位\n北京市体育基金会、北京市体育总会、北京市水上救生协会、北京市木樨园体育运动技术学校、北京市冬泳俱乐部、广西游泳协会\n五、合作单位\n华润怡宝饮料（中国）有限公司北京分公司、中国洲克控股有限公司、北京玉德堂医药科技有限公司、亚冠国际连锁健身俱乐部、壹健身、北京深蓝乐源教育科技有限公司、亚冠国际连锁健身俱乐部、河北康正药业有限公司、北京东霆健身有限公司、北京问成教育咨询有限公司、北京中意明安科技有限责任公司、北京善方医院、上海成浪网络技术有限公司\n六、竞赛时间和地点\n2018年10月5日-10月6日 国家游泳中心（水立方）\n七、竞赛项目\n50、100米自由泳\n50、100米仰泳\n50、100米蛙泳\n50、100米蝶泳\n200米混合泳（少儿）\n100米挑战世界冠军自由泳比赛\n（比赛方法：比赛当日，截止本项开始，已进行的项目冠军自愿报名与前国家游泳队优秀运动员、世界冠军同池竞赛。）\n三代3X25米自由泳接力赛\n男女混合2X50米自由泳接力（成人）\n团体4X50米自由泳接力（成人）\n50米浮球拖带救生赛\n（比赛方法：下载2018竞赛规程查看)\n50米救生圈家庭赛\n（比赛方法：下载2018竞赛规程查看)\n乐享水立方 老幼爱心赛 表演项目2-3项\n八、参加办法\n（一）分组规定\n1、京津冀团体组：\n（1）北京、天津、河北以省市名义组队参赛，每队限报40人，男、女年龄组不限。\n（2）根据报名情况确定编排和比赛。\n（3）设团体总分奖，个人项目按照9、7、6、5、4、3、2、1计分，接力项目计分加倍（参加接力项目运动员必须都是团体组成员）。\n2、俱乐部组：\n（1）俱乐部成员按照报名年龄编入上述组参加比赛，单独录取。\n（2）设立团体总分奖，个人项目按照9、7、6、5、4、3、2、1计分，接力项目计分加倍（参加接力项目运动员必须都是俱乐部成员）。\n3、残疾人组：\n根据残疾人报名情况组织参赛；采取单独报名、编排方式，请先致电010-84378938询问相关事宜。\n4、国际组：\n各国驻华使馆、驻京机构工作人员及子女。根据报名情况确定编排、比赛及录取办法。\n5、少儿男女组：\n2010年组（2010年出生） ，仅限报名参加50米比赛\n2009年组 （2009年出生），仅限报名参加50米比赛\n2008年组（2008年出生）\n2007年组（2007年出生）\n2006年组（2006年出生）\n2005-2004年组（2005至2004年出生）\n2003-2001年组（2001至2003年出生）\n6、青年男女组：\n18-29岁组（1989年至2000年）\n30-34岁组（1984年至1988年）\n35-39岁组（1979年至1983年）\n7、中年男女组：\n40-44岁组（1974年至1978年）\n45-49岁组（1969年至1973年）\n50-54岁组（1964年至1968年）\n8、老年男女组：\n55-59岁组（1959年至1963年）\n60-65岁组（1953年至1958年）\n9、三代3X25米自由泳接力赛：\n参加者必须是三代人（性别不限）\n第一代人年龄要求55周岁以上（1963年以前出生）\n第二代人年龄要求35—54周岁（1983年至1964年出生）\n第三代人年龄要求10周岁（含）以下（2008以后出生）比赛泳姿不限，顺序不限，距离为3 X 25米。\n10、男女混合2X50米自由泳接力：\n参赛运动员年龄必须18周岁以上（2000年以前出生）比赛泳姿不限，顺序不限。\n11、团体4X25米自由泳接力（成人）：\n每单位可报一至二个队，每队最少要有一名女性。参赛运动员年龄必须18周岁以上（2000年以前出生）比赛泳姿不限，顺序不限。\n12、乐享水立方：\n泳池体验活动，不限姿势不计时间，参加活动人员在工作人员指导下，下水游完50米即可。参加此体验活动的群众不得再报其他比赛项目。\n（二）参赛者需递交由本人签字的《自愿参加比赛责任书》，并交纳报名费100元人民币∕人（含25元保险费，每人限报两项，不含集体项目）即可完成报名，每人最多限报二项（不含接力和集体娱乐项目）。国际组报名费20美元∕人（含保险费）。\n（三）团体单位报名参赛必须派选手参加至少2项集体项目。\n（四）运动员身体状况要求\n游泳竞赛是一项负荷强度较大的运动，对参与者身体状况有较高的要求，参与者应身体健康，有长期参加游泳锻炼或训练的基础。参与者可根据自己的身体状况和实际能力，选择报名。组委会要求参与者赛前去相应医疗机构进行健康体检。\n有以下疾病患者不宜参加比赛：\n1、先天性心脏病和风湿性心脏病；\n2、高血压和脑血管疾病；\n3、心肌炎和其它心脏病；\n4、冠状动脉病和严重心律不齐；\n5、血糖过高或过低的糖尿病；\n6、不适合本项运动者。\n在比赛中，因个人身体及其它个人原因导致的人身损害和财产损失，由参赛者个人承担责任。\n（五）现役运动员不得参赛。\n（六）大会期间提供医疗服务，凡医疗服务超出现场救助范围的，所需费用自理。\n九、报名须知\n1、本次公开赛报名时间为：2018年9月12日18：00 - 9月22日18：00。 \n2、本次公开赛报名分为线上报名和线下报名两种方式，其中线上报名方式的缴费需选择在线网银或支付宝方式支付，线下报名由北京市游泳运动协会负责，咨询电话：15600708323，咨询人：王老师。 \n3、游泳竞赛是一项负荷强度较大的运动，对参与者身体状况有较高的要求，本次公开赛允许参赛报名的最小年龄为5岁(2013年出生)，最大年龄为80岁(1938年出生)。 \n4、所有参赛个人或团体正式报名前，应仔细阅读本须知，并按要求提交参赛人员的报名信息，提交的内容务必真实、完整、准确。本次公开赛报名一经缴费确认后，不得再修改报名信息及报名项目，且不予退款。 \n5、本次公开赛的报名需提交参赛人员的头像照片，可通过线上拍照上传或本地上传，及线下现场拍照或提交照片方式完成。照片的要求为一寸以上大头照，2M大小以内。照片等信息审核通过后，才可进行下一步的缴费操作。头像照片一经审核通过后，将不得修改。\n6、每位参赛者的身份证号码或护照号码作为报名时的唯一标识号码。同一位参赛者在个人入口报名时，不得重复再次报名参加比赛。同一位参赛者可同时参加个人和团体项目，但个人单项不得重复参赛；若参赛者是通过个人和团体入口同时报名，需缴纳两次报名费用，且个人单项不得重复参加。 \n7、未满14岁的儿童参加比赛，需有家长全程陪同。比赛日亲友票为60元/人。 \n8、报名参加接力项目的参赛者，若任一接力组合中的某一位参赛者取消参赛，此接力组合亦同时取消参赛。\n9、网上报名时，参赛个人或团体可通过报名时在网站(www.naclub.cn)上注册的账号查询个人或团体的报名、缴费、物品领用、比赛成绩等信息。 \n10、本次公开赛的《竞赛日程》安排将会根据比赛实际报名的人数稍后公布。若出现当日参赛人数超出场馆泳池最大承受量，当天部分项目将会顺延至第二天进行，关于上述竞赛日程的变更，组委会将会提前短信通知参赛者。 \n11、所有参赛个人/团体须在赛前根据组委会提前的短信通知，由本人或监护人持身份证等有效证件到指定地点领取参赛证件及比赛物品，签署《自愿参加比赛责任书》。(团体报名可由团体单位负责人携带盖有本单位公章的每参赛者签字的《自愿参加比赛责任书》，统一领取本团体单位所有参赛证件及比赛物品。) \n12、各参赛个人/团体报名前请仔细阅读北京市游泳协会下发的《2018京津冀游泳公开赛暨第五届北京市全民游泳大赛竞赛规程》。\n十、竞赛办法\n（一）本次比赛执行中国游泳协会审定的2014---2018《游泳竞赛规则》。\n（二）所有比赛项目采用一次性决赛，按成绩排列名次，成绩相同名次并列，取消下一名次。\n（三）如每项组报名人数不足三人，则取消该项组比赛，运动员可改报其他项目。\n（四）运动员参赛时，必须向检录裁判员出示本届赛会《参赛证》，无证者不得参赛。\n（五）凡冒名顶替和弄虚作假者，均取消本人所有比赛成绩。\n十一、录取名次与奖励\n（一）各年龄组录取前8名（报名不足8名，减二录取），颁发北京市游泳运动协会统一制作的竞赛证书及前三名奖牌。\n十二、仲裁与裁判\n仲裁、裁判长、裁判员由主办单位选派。');
INSERT INTO `sys_competition` VALUES (4, 'ACE无人机竞速挑战赛', 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/be72.jpg', '2023-05-25 00:00:00', '2023-05-26 00:00:00', 0, 1, '2023-05-24 00:00:00', '广东海洋大学', 'ACE无人机竞速挑战赛以地区为分赛场，以竞速无人机为竞赛载体，鼓励竞速无人机爱好者和青少年参与。旨在促进区域性竞速无人机技术交流，为地区科技创新发展助力。以严谨的科技竞赛规则和前沿的科技创新手段，提升无人机竞速赛整体水平，推动无人机行业技术发展，同时激发无人机爱好者及青少年对科技创新的兴趣与热爱。\n四、参赛对象\n1、竞赛分为男子组、女子组和青少年组三组进行。\n2、赛制\n（一）海选赛\n所有选手通过抽签顺序进行分组比赛，每组 4 人。比赛共1轮，每轮飞行2圈。\n炸机未完成者直接淘汰。\n排名前64名的选手进入循环赛。\n（二）循环赛\n按照海选赛成绩排名，参赛选手分为16组，每组 4人。\n比赛分为3轮，每轮飞行2圈，每轮为一个起降，选取三轮最好成绩（时间最短成绩）为最终成绩。一轮未完成，最终成绩加时10秒，两轮未完成，最终成绩加时20秒，三轮未完成淘汰。\n成绩排名前16的选手晋级决赛。\n附：（超时5分钟未到视为自动弃权。）\n五、参赛报名\n1、登录大赛官网www.acefpv.cn 自行报名\n2、联系赛事负责人：高老师  18647914412\n六、报名费用　　\n       本次比赛不收取任何费用。交通住宿费用户自理。比赛所用器材和耗材由参赛者自行准备，并对设备的安全性负责。\n七、奖项设置\n区域赛奖金取前3名\n第一名奖金3000RMB\n第二名奖金2000RMB\n第三名奖金1000RMB\n并颁发获奖证书，奖杯\n总决赛奖金共取3名，外加优秀奖3名\n第一名：10,000/RMB；\n第二名：8,000/RMB；\n第三名：6,000/RMB；\n第4名到第6名：1,000/RMB/人；\n同时前三名颁发证书、奖杯；第4-6名颁发奖章\n八、参赛设备\na) 参赛模型必须是四旋翼（含）以内。150mm轴距以上，600mm轴距以下，起飞重量不超过1200g。机身不允许使用可燃的塑料、木材。最大外尺寸不大于700mmx700mm。\nb) 必须使用无刷动力系统，电机定子直径45mm以内，KV不限。\nc) 必须使用塑料螺旋桨，禁止使用金属、碳纤维、玻璃纤维材质的螺旋桨。每轴允许使用一个螺旋桨。\nd) 可使用2.4Ghz遥控器，可以使用外置高频头，不得改装功率放大设备。\ne) 允许使用自稳模式（自动水平）和角速度模式（手动模式），不允许使用GPS、光流、避障系统。必须设置熄火开关，开启一键锁机模式。比赛过程允许使用“乌龟”模式，在不接触模型的情况下，翻转模型。\nf) 使用LIPO（锂聚合物电池）。串联数量3-6S，最高电压限制为27V。标识清楚，外观完整无破皮损坏。\ng) 必须使用模拟制式图传或者使用数字图传。中心频点5.8Ghz，可切换25mw/100mw/200mw。品牌不限，裁判组对功率超标、频点漂移等可能影响比赛进行的器材，将不予检录通过。\n九、联系方式\n1、主办方电话：18647914412 或 18704791488');
INSERT INTO `sys_competition` VALUES (6, '郭建', 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/0f71.jpg', '2023-05-31 00:00:00', '2023-06-01 00:00:00', 0, 1, '2023-05-29 09:40:13', '广东', '广东');

-- ----------------------------
-- Table structure for sys_equipment
-- ----------------------------
DROP TABLE IF EXISTS `sys_equipment`;
CREATE TABLE `sys_equipment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '器材id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '器材名称',
  `classification` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '器材类别',
  `surplus` bigint(0) NOT NULL COMMENT '器材剩余数量',
  `total` bigint(0) NOT NULL COMMENT '器材总数',
  `money` int(0) NOT NULL COMMENT '器材金额',
  `status` int(0) NOT NULL COMMENT '0-可租用，1-不可租用',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_equipment
-- ----------------------------
INSERT INTO `sys_equipment` VALUES (1, '篮球', '球类', 230, 300, 15, 0, '2023-05-20 00:00:00');
INSERT INTO `sys_equipment` VALUES (2, '羽毛球', '球类', 148, 200, 30, 0, '2023-05-20 00:00:00');
INSERT INTO `sys_equipment` VALUES (3, '足球', '球类', 289, 300, 15, 0, '2023-05-26 00:00:00');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(0) NOT NULL COMMENT '类型     0：目录   1：菜单   2：按钮',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `orderNum` int(0) NULL DEFAULT NULL COMMENT '排序',
  `created` datetime(0) NOT NULL,
  `updated` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', 'sys:manage', '', 0, 'el-icon-s-operation', 1, '2021-01-15 18:58:18', '2021-01-15 18:58:20', 1);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '/sys/users', 'sys:user:list', 'sys/User', 1, 'el-icon-s-custom', 1, '2021-01-15 19:03:45', '2023-05-21 20:51:36', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '/sys/roles', 'sys:role:list', 'sys/Role', 1, 'el-icon-rank', 2, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '/sys/menus', 'sys:menu:list', 'sys/Menu', 1, 'el-icon-menu', 3, '2021-01-15 19:03:45', '2021-01-15 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (5, 0, '器材管理', '', 'sys:tools', NULL, 0, 'el-icon-s-tools', 2, '2021-01-15 19:06:11', NULL, 1);
INSERT INTO `sys_menu` VALUES (6, 5, '器材列表管理', '/equipment/List', 'sys:equipment:list', 'equipment/List', 1, 'el-icon-tickets', 1, '2021-01-15 19:07:18', '2023-05-20 19:40:28', 1);
INSERT INTO `sys_menu` VALUES (7, 3, '添加角色', '', 'sys:role:save', '', 2, '', 1, '2021-01-15 23:02:25', '2023-05-18 21:44:14', 1);
INSERT INTO `sys_menu` VALUES (9, 2, '添加用户', NULL, 'sys:user:save', NULL, 2, NULL, 1, '2021-01-17 21:48:32', NULL, 1);
INSERT INTO `sys_menu` VALUES (10, 2, '修改用户', NULL, 'sys:user:update', NULL, 2, NULL, 2, '2021-01-17 21:49:03', '2021-01-17 21:53:04', 1);
INSERT INTO `sys_menu` VALUES (11, 2, '删除用户', NULL, 'sys:user:delete', NULL, 2, NULL, 3, '2021-01-17 21:49:21', NULL, 1);
INSERT INTO `sys_menu` VALUES (12, 2, '分配角色', NULL, 'sys:user:role', NULL, 2, NULL, 4, '2021-01-17 21:49:58', NULL, 1);
INSERT INTO `sys_menu` VALUES (13, 2, '重置密码', NULL, 'sys:user:repass', NULL, 2, NULL, 5, '2021-01-17 21:50:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (14, 3, '修改角色', NULL, 'sys:role:update', NULL, 2, NULL, 2, '2021-01-17 21:51:14', NULL, 1);
INSERT INTO `sys_menu` VALUES (15, 3, '删除角色', NULL, 'sys:role:delete', NULL, 2, NULL, 3, '2021-01-17 21:51:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (16, 3, '分配权限', NULL, 'sys:role:perm', NULL, 2, NULL, 5, '2021-01-17 21:52:02', NULL, 1);
INSERT INTO `sys_menu` VALUES (17, 4, '添加菜单', NULL, 'sys:menu:save', NULL, 2, NULL, 1, '2021-01-17 21:53:53', '2021-01-17 21:55:28', 1);
INSERT INTO `sys_menu` VALUES (18, 4, '修改菜单', NULL, 'sys:menu:update', NULL, 2, NULL, 2, '2021-01-17 21:56:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (19, 4, '删除菜单', NULL, 'sys:menu:delete', NULL, 2, NULL, 3, '2021-01-17 21:56:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (21, 1, '公告管理', '/sys/Notice', 'sys:notice:list', 'sys/Notice', 1, 'el-icon-document', 4, '2023-05-18 21:40:30', '2023-05-19 18:57:50', 1);
INSERT INTO `sys_menu` VALUES (22, 21, '添加公告', NULL, 'sys:notice:save', NULL, 2, NULL, 1, '2023-05-18 21:44:01', NULL, 1);
INSERT INTO `sys_menu` VALUES (23, 21, '修改公告', NULL, 'sys:notice:update', NULL, 2, NULL, 2, '2023-05-18 21:44:58', NULL, 1);
INSERT INTO `sys_menu` VALUES (24, 21, '删除公告', NULL, 'sys:notice:delete', NULL, 2, NULL, 3, '2023-05-18 21:45:38', NULL, 1);
INSERT INTO `sys_menu` VALUES (25, 37, '场地信息管理', '/sys/Place', 'sys:place:list', 'sys/Place', 1, 'el-icon-place', 1, '2023-05-19 13:22:12', '2023-05-25 20:34:13', 1);
INSERT INTO `sys_menu` VALUES (26, 25, '添加场地信息', NULL, 'sys:place:save', NULL, 2, NULL, 1, '2023-05-19 13:22:56', NULL, 1);
INSERT INTO `sys_menu` VALUES (27, 25, '修改场地信息', NULL, 'sys:place:update', NULL, 2, NULL, 2, '2023-05-19 13:23:35', NULL, 1);
INSERT INTO `sys_menu` VALUES (28, 25, '删除场地信息', NULL, 'sys:place:delete', NULL, 2, NULL, 3, '2023-05-19 13:24:11', NULL, 1);
INSERT INTO `sys_menu` VALUES (29, 6, '添加器材', NULL, 'sys:equipment:save', NULL, 2, '', 1, '2023-05-20 19:43:15', NULL, 1);
INSERT INTO `sys_menu` VALUES (30, 6, '编辑器材', NULL, 'sys:equipment:update', NULL, 2, NULL, 2, '2023-05-20 19:44:10', NULL, 1);
INSERT INTO `sys_menu` VALUES (31, 6, '删除器材', NULL, 'sys:equipment:delete', NULL, 2, NULL, 3, '2023-05-20 19:44:37', NULL, 1);
INSERT INTO `sys_menu` VALUES (32, 5, '器材租借管理', '/sys/Borrow', 'sys:borrow:list', 'sys/Borrow', 1, 'el-icon-tickets', 2, '2023-05-21 16:57:40', '2023-05-21 16:59:55', 1);
INSERT INTO `sys_menu` VALUES (33, 32, '器材租借审批', NULL, 'sys:borrow:approve', NULL, 2, NULL, 1, '2023-05-21 17:03:01', NULL, 1);
INSERT INTO `sys_menu` VALUES (36, 0, '赛事管理', NULL, 'sys:competition', NULL, 0, 'el-icon-baseball', 1, '2023-05-23 22:15:05', NULL, 1);
INSERT INTO `sys_menu` VALUES (37, 0, '场地管理', NULL, 'sys:place', NULL, 0, 'el-icon-location-outline', 1, '2023-05-23 22:16:58', NULL, 1);
INSERT INTO `sys_menu` VALUES (38, 36, '赛事列表管理', '/sys/Competition', 'sys:competition:list', 'sys/Competition', 1, 'el-icon-soccer', 1, '2023-05-23 23:12:30', NULL, 1);
INSERT INTO `sys_menu` VALUES (39, 38, '添加赛事', NULL, 'sys:competition:save', NULL, 2, NULL, 1, '2023-05-23 23:13:33', NULL, 1);
INSERT INTO `sys_menu` VALUES (40, 38, '修改赛事', NULL, 'sys:competition:update', NULL, 2, NULL, 2, '2023-05-23 23:14:27', NULL, 1);
INSERT INTO `sys_menu` VALUES (41, 38, '删除赛事', NULL, 'sys:competition:delete', NULL, 2, NULL, 3, '2023-05-23 23:15:01', NULL, 1);
INSERT INTO `sys_menu` VALUES (42, 37, '场地预约管理', '/sys/Appointment', 'sys:appointment:list', 'sys/Appointment', 1, 'el-icon-s-order', 2, '2023-05-25 20:33:46', '2023-05-25 20:34:08', 1);
INSERT INTO `sys_menu` VALUES (43, 42, '禁止预约场地', NULL, 'sys:appointment:forbid', NULL, 2, NULL, 1, '2023-05-25 20:45:53', NULL, 1);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '公告Id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告内容',
  `writer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布公告的用户名',
  `status` int(0) NOT NULL COMMENT '0-发布，1-撤回，2-暂存',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '公告创建时间',
  `updated` datetime(0) NULL DEFAULT NULL COMMENT '公告修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (3, '我校参加2022年广东省体育行业职业技能比赛获得佳绩', '由广东省体育局主办，广东省体育职业技术学院和广东省体育行业特有工种职业技能鉴定站承办的2022年（因疫情比赛推迟）广东省体育行业职业技能竞赛游泳救生员竞赛于2023年4月14-16日在珠海体育中心游泳馆举行。来自全省29个单位、24支队伍共158名运动员分别参加团体竞赛和个人竞赛。我校代表队参加竞赛设置的4*25米假人接力和模拟救生两项团体比赛，模拟救生团体第二名，团体竞赛总分第二，为学校争得了荣誉。', '张三', 0, '2023-05-18 00:00:00', NULL);
INSERT INTO `sys_notice` VALUES (4, '我校代表队在中国龙舟公开赛中取得优秀成绩', '2023年4月22日至23日，学校女子龙舟队在陈日响教练的带领下，共16人参加了由中国龙舟协会主办的中国龙舟公开赛女子组12人100M、200M、500M直道赛决赛，均取得了第五名的优秀成绩。本次比赛共有20支队伍参加，其中有来自聊城大学、青岛大学、华中科技大学、四川职业技术学院等专业运动强队。在时间紧，任务重，新老队员磨合时间短的情况下，学校女子龙舟队14名队员在陈日响教练的精心指导下科学训练，积极备战。两天赛程中队员们在9度低温中顶风冒雨，不畏强手勇于拼搏，努力发扬“坚韧不拔，自强不息”的海大精神和“永不放弃，超越自我”的体育精神，为学校争得了荣誉。同时也向优秀的运动队伍学习了科学的训练方法，队员们也积累了丰富的比赛经验，为下步训练和比赛打下了坚实的基础。', '李四', 0, '2023-05-18 00:00:00', NULL);
INSERT INTO `sys_notice` VALUES (8, '我校在广东省大学生乒乓球锦标赛中获得佳绩', '2023年5月17-21日，广东省2022年大学生乒乓球锦标赛在华南师范大学体育馆举行。本次乒乓球锦标赛共有94所高校898名运动员参赛，分为循环赛、交叉淘汰、八强赛三个阶段，赛场竞争异常激烈。\n\n我校派出12名队员，参加甲组男女单打、男女双打、混合双打的争夺。乒乓健儿在比赛中克服困难、顽强拼搏，展现了坚韧不拔的意志品质，获得混合双打冠军（江祥龙、连苡），男子双打第六名（王子钊、黄锐），甲组团体总分并列第四名，创造校乒乓球队在该项目的历史最好成绩。', '张三', 0, '2023-05-28 18:31:25', NULL);
INSERT INTO `sys_notice` VALUES (9, '杀杀杀', '是', '实时', 0, '2023-05-29 10:13:51', NULL);

-- ----------------------------
-- Table structure for sys_place
-- ----------------------------
DROP TABLE IF EXISTS `sys_place`;
CREATE TABLE `sys_place`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '场地名称',
  `people` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '场地地址',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '场地描述',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(0) NOT NULL COMMENT '状态，0-发布，1-未发布，2-暂存',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场地图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_place
-- ----------------------------
INSERT INTO `sys_place` VALUES (1, '乒乓球场', '李四', '18138808679', '广东海洋大学体育馆', '广东海洋大学体育馆', '2023-05-19 00:00:00', '2023-05-28 19:06:21', 0, 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/1e24.jpeg');
INSERT INTO `sys_place` VALUES (2, '篮球场', '李四', '18138808679', '广东海洋大学篮球场', '广东海洋大学篮球场', '2023-05-19 00:00:00', '2023-05-19 00:00:00', 0, 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/eea3.jpeg');
INSERT INTO `sys_place` VALUES (3, '游泳池', '赵武', '18138808679', '广东海洋大学游泳池', '广东海洋大学游泳池', '2023-05-19 00:00:00', '2023-05-19 00:00:00', 0, 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/27ca.jpg');
INSERT INTO `sys_place` VALUES (4, '排球场', '张三', '18138808679', '广东海洋大学排球场', '广东海洋大学排球场11111111', '2023-05-19 00:00:00', '2023-05-29 09:50:03', 0, 'https://dbmanagement.oss-cn-shenzhen.aliyuncs.com/itsource/f1b5.jpg');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `created` datetime(0) NULL DEFAULT NULL,
  `updated` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (3, '普通用户', 'normal', '只有基本查看功能', '2021-01-04 10:09:14', '2021-01-30 08:19:52', 1);
INSERT INTO `sys_role` VALUES (6, '超级管理员', 'admin', '系统默认最高权限，不可以编辑和任意修改', '2021-01-16 13:29:03', '2021-01-17 15:50:45', 1);
INSERT INTO `sys_role` VALUES (13, '器材管理员', 'equipment', '管理体育器材', '2023-05-18 12:59:36', NULL, 1);
INSERT INTO `sys_role` VALUES (14, '赛事管理员', 'competition', '管理体育赛事', '2023-05-18 13:00:52', NULL, 1);
INSERT INTO `sys_role` VALUES (15, '场地管理员', 'place', '管理体育场地', '2023-05-18 13:01:46', NULL, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(0) NOT NULL,
  `menu_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_role_roleId`(`role_id`) USING BTREE,
  INDEX `FK_menu_menuId`(`menu_id`) USING BTREE,
  CONSTRAINT `FK_menu_menuId` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_role_roleId` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1083 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1015, 6, 1);
INSERT INTO `sys_role_menu` VALUES (1016, 6, 2);
INSERT INTO `sys_role_menu` VALUES (1017, 6, 9);
INSERT INTO `sys_role_menu` VALUES (1018, 6, 10);
INSERT INTO `sys_role_menu` VALUES (1019, 6, 11);
INSERT INTO `sys_role_menu` VALUES (1020, 6, 12);
INSERT INTO `sys_role_menu` VALUES (1021, 6, 13);
INSERT INTO `sys_role_menu` VALUES (1022, 6, 3);
INSERT INTO `sys_role_menu` VALUES (1023, 6, 7);
INSERT INTO `sys_role_menu` VALUES (1024, 6, 14);
INSERT INTO `sys_role_menu` VALUES (1025, 6, 15);
INSERT INTO `sys_role_menu` VALUES (1026, 6, 16);
INSERT INTO `sys_role_menu` VALUES (1027, 6, 4);
INSERT INTO `sys_role_menu` VALUES (1028, 6, 17);
INSERT INTO `sys_role_menu` VALUES (1029, 6, 18);
INSERT INTO `sys_role_menu` VALUES (1030, 6, 19);
INSERT INTO `sys_role_menu` VALUES (1031, 6, 21);
INSERT INTO `sys_role_menu` VALUES (1032, 6, 22);
INSERT INTO `sys_role_menu` VALUES (1033, 6, 23);
INSERT INTO `sys_role_menu` VALUES (1034, 6, 24);
INSERT INTO `sys_role_menu` VALUES (1035, 6, 36);
INSERT INTO `sys_role_menu` VALUES (1036, 6, 38);
INSERT INTO `sys_role_menu` VALUES (1037, 6, 39);
INSERT INTO `sys_role_menu` VALUES (1038, 6, 40);
INSERT INTO `sys_role_menu` VALUES (1039, 6, 41);
INSERT INTO `sys_role_menu` VALUES (1040, 6, 37);
INSERT INTO `sys_role_menu` VALUES (1041, 6, 25);
INSERT INTO `sys_role_menu` VALUES (1042, 6, 26);
INSERT INTO `sys_role_menu` VALUES (1043, 6, 27);
INSERT INTO `sys_role_menu` VALUES (1044, 6, 28);
INSERT INTO `sys_role_menu` VALUES (1045, 6, 42);
INSERT INTO `sys_role_menu` VALUES (1046, 6, 43);
INSERT INTO `sys_role_menu` VALUES (1047, 6, 5);
INSERT INTO `sys_role_menu` VALUES (1048, 6, 6);
INSERT INTO `sys_role_menu` VALUES (1049, 6, 29);
INSERT INTO `sys_role_menu` VALUES (1050, 6, 30);
INSERT INTO `sys_role_menu` VALUES (1051, 6, 31);
INSERT INTO `sys_role_menu` VALUES (1052, 6, 32);
INSERT INTO `sys_role_menu` VALUES (1053, 6, 33);
INSERT INTO `sys_role_menu` VALUES (1059, 15, 37);
INSERT INTO `sys_role_menu` VALUES (1060, 15, 25);
INSERT INTO `sys_role_menu` VALUES (1061, 15, 26);
INSERT INTO `sys_role_menu` VALUES (1062, 15, 27);
INSERT INTO `sys_role_menu` VALUES (1063, 15, 28);
INSERT INTO `sys_role_menu` VALUES (1064, 15, 42);
INSERT INTO `sys_role_menu` VALUES (1065, 15, 43);
INSERT INTO `sys_role_menu` VALUES (1071, 13, 5);
INSERT INTO `sys_role_menu` VALUES (1072, 13, 6);
INSERT INTO `sys_role_menu` VALUES (1073, 13, 29);
INSERT INTO `sys_role_menu` VALUES (1074, 13, 30);
INSERT INTO `sys_role_menu` VALUES (1075, 13, 31);
INSERT INTO `sys_role_menu` VALUES (1076, 13, 32);
INSERT INTO `sys_role_menu` VALUES (1077, 13, 33);
INSERT INTO `sys_role_menu` VALUES (1078, 14, 36);
INSERT INTO `sys_role_menu` VALUES (1079, 14, 38);
INSERT INTO `sys_role_menu` VALUES (1080, 14, 39);
INSERT INTO `sys_role_menu` VALUES (1081, 14, 40);
INSERT INTO `sys_role_menu` VALUES (1082, 14, 41);
INSERT INTO `sys_role_menu` VALUES (1083, 3, 5);
INSERT INTO `sys_role_menu` VALUES (1084, 3, 6);
INSERT INTO `sys_role_menu` VALUES (1085, 3, 29);
INSERT INTO `sys_role_menu` VALUES (1086, 3, 30);
INSERT INTO `sys_role_menu` VALUES (1087, 3, 31);
INSERT INTO `sys_role_menu` VALUES (1088, 3, 32);
INSERT INTO `sys_role_menu` VALUES (1089, 3, 33);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NULL DEFAULT NULL,
  `updated` datetime(0) NULL DEFAULT NULL,
  `last_login` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_USERNAME`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$usDc05oX7v7p78eY.g7is..cN6n8o84gz4xESe.YLun8k/hy7M/4q', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', '123@qq.com', '广州', '2021-01-12 22:13:53', '2023-05-29 09:08:51', '2020-12-30 08:38:37', 1);
INSERT INTO `sys_user` VALUES (2, 'normal', '$2a$10$usDc05oX7v7p78eY.g7is..cN6n8o84gz4xESe.YLun8k/hy7M/4q', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 'test@qq.com', NULL, '2021-01-30 08:20:22', '2023-05-28 18:24:59', NULL, 1);
INSERT INTO `sys_user` VALUES (4, 'equip', '$2a$10$usDc05oX7v7p78eY.g7is..cN6n8o84gz4xESe.YLun8k/hy7M/4q', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 'sdas@qq.com', NULL, '2022-12-27 10:27:50', '2023-05-28 19:39:41', NULL, 1);
INSERT INTO `sys_user` VALUES (6, 'place', '$2a$10$usDc05oX7v7p78eY.g7is..cN6n8o84gz4xESe.YLun8k/hy7M/4q', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 'place@qq.com', NULL, '2023-05-28 19:13:32', '2023-05-28 19:36:14', NULL, 1);
INSERT INTO `sys_user` VALUES (7, 'comp', '$2a$10$usDc05oX7v7p78eY.g7is..cN6n8o84gz4xESe.YLun8k/hy7M/4q', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', 'sdasds@qq.com', NULL, '2023-05-28 19:41:54', '2023-05-28 19:42:12', NULL, 1);
INSERT INTO `sys_user` VALUES (9, 'test', '$2a$10$2sJki8vK8yWaKvJwUsR9VOf/EA1Fr6qH.C/4tR75w98IIn4lK8qe6', 'https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg', '222@qq.com', NULL, '2023-05-29 10:11:15', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL,
  `role_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_user_userId`(`user_id`) USING BTREE,
  INDEX `FK_roleId`(`role_id`) USING BTREE,
  CONSTRAINT `FK_roleId` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_user_userId` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (4, 1, 6);
INSERT INTO `sys_user_role` VALUES (7, 1, 3);
INSERT INTO `sys_user_role` VALUES (13, 2, 3);
INSERT INTO `sys_user_role` VALUES (18, 4, 13);
INSERT INTO `sys_user_role` VALUES (19, 6, 15);
INSERT INTO `sys_user_role` VALUES (20, 7, 14);
INSERT INTO `sys_user_role` VALUES (21, 9, 3);

-- ----------------------------
-- Table structure for user_apply
-- ----------------------------
DROP TABLE IF EXISTS `user_apply`;
CREATE TABLE `user_apply`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '参赛信息id',
  `competitionId` bigint(0) NOT NULL COMMENT '竞赛id',
  `userId` bigint(0) NOT NULL COMMENT '用户id',
  `created` datetime(0) NOT NULL COMMENT '用户参赛时间',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '用户参赛状态，0-已报名，1-取消报名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_apply_competitionId`(`competitionId`) USING BTREE,
  INDEX `FK_apply_userId`(`userId`) USING BTREE,
  CONSTRAINT `FK_apply_competitionId` FOREIGN KEY (`competitionId`) REFERENCES `sys_competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_apply_userId` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_apply
-- ----------------------------
INSERT INTO `user_apply` VALUES (1, 2, 1, '2023-05-26 00:00:00', 0);
INSERT INTO `user_apply` VALUES (3, 2, 2, '2023-05-24 00:00:00', 0);
INSERT INTO `user_apply` VALUES (4, 3, 1, '2023-05-26 00:00:00', 1);
INSERT INTO `user_apply` VALUES (7, 6, 1, '2023-05-29 10:18:42', 1);

-- ----------------------------
-- Table structure for user_appointment
-- ----------------------------
DROP TABLE IF EXISTS `user_appointment`;
CREATE TABLE `user_appointment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '预约订单编号',
  `userId` bigint(0) NOT NULL COMMENT '预约用户编号',
  `placeId` bigint(0) NOT NULL COMMENT '场地编号',
  `orderDate` date NOT NULL COMMENT '预约日期',
  `orderStart` int(0) NOT NULL COMMENT '预约开始时间',
  `orderEnd` int(0) NOT NULL COMMENT '预约结束时间',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '预约状态，0-已预约，1-取消预约',
  `price` bigint(0) NOT NULL COMMENT '预约订单价格',
  `created` datetime(0) NOT NULL COMMENT '预约订单生成时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_appointment_userId`(`userId`) USING BTREE,
  INDEX `FK_appointment_placeId`(`placeId`) USING BTREE,
  CONSTRAINT `FK_appointment_placeId` FOREIGN KEY (`placeId`) REFERENCES `sys_place` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_appointment_userId` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_appointment
-- ----------------------------
INSERT INTO `user_appointment` VALUES (1, 1, 1, '2023-05-26', 8, 9, 0, 15, '2023-05-25 20:21:17');
INSERT INTO `user_appointment` VALUES (2, 2, 1, '2023-05-26', 9, 10, 0, 15, '2023-05-25 21:35:22');
INSERT INTO `user_appointment` VALUES (4, 1, 1, '2023-05-26', 12, 13, 0, 15, '2023-05-25 22:00:17');
INSERT INTO `user_appointment` VALUES (5, 1, 1, '2023-05-26', 14, 15, 0, 15, '2023-05-25 22:08:29');
INSERT INTO `user_appointment` VALUES (6, 2, 2, '2023-05-26', 12, 13, 0, 15, '2023-05-25 22:10:49');
INSERT INTO `user_appointment` VALUES (7, 2, 2, '2023-05-27', 11, 12, 0, 15, '2023-05-25 22:10:58');
INSERT INTO `user_appointment` VALUES (8, 2, 2, '2023-05-26', 11, 12, 0, 15, '2023-05-25 22:13:48');
INSERT INTO `user_appointment` VALUES (9, 2, 3, '2023-05-26', 10, 11, 0, 15, '2023-05-25 22:21:07');
INSERT INTO `user_appointment` VALUES (10, 1, 2, '2023-05-27', 12, 13, 1, 15, '2023-05-25 23:48:10');
INSERT INTO `user_appointment` VALUES (11, 1, 2, '2023-05-27', 15, 16, 0, 15, '2023-05-25 23:51:28');
INSERT INTO `user_appointment` VALUES (12, 1, 1, '2023-05-27', 8, 9, 0, 15, '2023-05-26 08:23:41');
INSERT INTO `user_appointment` VALUES (13, 2, 1, '2023-05-27', 11, 12, 0, 15, '2023-05-26 08:24:37');
INSERT INTO `user_appointment` VALUES (14, 1, 3, '2023-05-28', 12, 13, 0, 15, '2023-05-27 17:47:18');
INSERT INTO `user_appointment` VALUES (15, 1, 1, '2023-05-28', 11, 12, 0, 15, '2023-05-27 22:13:16');
INSERT INTO `user_appointment` VALUES (16, 2, 2, '2023-05-28', 11, 12, 0, 15, '2023-05-27 22:14:04');
INSERT INTO `user_appointment` VALUES (17, 1, 1, '2023-05-29', 14, 15, 1, 15, '2023-05-28 19:45:57');
INSERT INTO `user_appointment` VALUES (18, 1, 1, '2023-05-30', 13, 14, 1, 15, '2023-05-29 08:01:08');
INSERT INTO `user_appointment` VALUES (19, 2, 3, '2023-05-30', 13, 14, 0, 15, '2023-05-29 08:01:26');
INSERT INTO `user_appointment` VALUES (20, 6, 1, '2023-05-30', 12, 13, 0, 15, '2023-05-29 09:50:51');
INSERT INTO `user_appointment` VALUES (21, 7, 1, '2023-05-30', 15, 16, 0, 15, '2023-05-29 09:55:20');

-- ----------------------------
-- Table structure for user_borrow
-- ----------------------------
DROP TABLE IF EXISTS `user_borrow`;
CREATE TABLE `user_borrow`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '租用订单编号',
  `userId` bigint(0) NOT NULL COMMENT '用户id',
  `equipmentId` bigint(0) NOT NULL COMMENT '器材id',
  `number` int(0) NOT NULL COMMENT '数量',
  `totalMoney` bigint(0) NOT NULL COMMENT '总租用金额',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0-待审批，1-通过，2-驳回，3-归还，4-归还成功',
  `startTime` datetime(0) NULL DEFAULT NULL COMMENT '租用开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '租用结束时间',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_userId`(`userId`) USING BTREE,
  INDEX `FK_equipmentId`(`equipmentId`) USING BTREE,
  CONSTRAINT `FK_equipmentId` FOREIGN KEY (`equipmentId`) REFERENCES `sys_equipment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_userId` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_borrow
-- ----------------------------
INSERT INTO `user_borrow` VALUES (4, 1, 1, 6, 15, 4, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (5, 1, 1, 1, 15, 1, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (6, 1, 1, 6, 15, 0, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (7, 1, 1, 1, 15, 0, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (8, 1, 1, 3, 15, 0, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (9, 1, 2, 5, 210, 0, '2023-05-23 00:00:00', '2023-05-30 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (10, 1, 2, 1, 240, 0, '2023-05-23 00:00:00', '2023-05-31 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (11, 1, 2, 1, 210, 0, '2023-05-24 00:00:00', '2023-05-31 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (12, 1, 2, 1, 30, 0, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (13, 1, 2, 1, 30, 0, '2023-05-30 00:00:00', '2023-05-31 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (14, 1, 2, 1, 210, 0, '2023-05-23 00:00:00', '2023-05-30 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (15, 1, 2, 1, 0, 0, '2023-05-24 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (16, 1, 1, 1, 15, 0, '2023-05-23 00:00:00', '2023-05-24 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (17, 1, 1, 1, 30, 0, '2023-05-23 00:00:00', '2023-05-25 00:00:00', '2023-05-22 00:00:00');
INSERT INTO `user_borrow` VALUES (18, 2, 1, 3, 15, 4, '2023-05-26 00:00:00', '2023-05-27 00:00:00', '2023-05-25 00:00:00');
INSERT INTO `user_borrow` VALUES (19, 2, 1, 1, 15, 4, '2023-05-27 00:00:00', '2023-05-28 00:00:00', '2023-05-26 00:00:00');
INSERT INTO `user_borrow` VALUES (20, 1, 3, 10, 15, 0, '2023-05-27 00:00:00', '2023-05-28 00:00:00', '2023-05-26 00:00:00');
INSERT INTO `user_borrow` VALUES (21, 2, 1, 2, 15, 0, '2023-05-27 00:00:00', '2023-05-28 00:00:00', '2023-05-26 00:00:00');
INSERT INTO `user_borrow` VALUES (22, 2, 1, 1, 15, 4, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (23, 2, 1, 1, 15, 4, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (24, 2, 1, 1, 15, 4, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (25, 2, 1, 1, 15, 0, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (26, 2, 1, 1, 15, 0, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (27, 2, 1, 1, 15, 4, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (47, 2, 1, 1, 15, 0, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (79, 2, 2, 1, 30, 0, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (80, 1, 1, 1, 15, 4, '2023-05-28 00:00:00', '2023-05-29 00:00:00', '2023-05-27 00:00:00');
INSERT INTO `user_borrow` VALUES (81, 2, 1, 1, 15, 4, '2023-05-29 08:00:00', '2023-05-30 08:00:00', '2023-05-28 00:00:00');
INSERT INTO `user_borrow` VALUES (82, 2, 1, 1, 15, 4, '2023-05-29 08:00:00', '2023-05-30 08:00:00', '2023-05-28 00:00:00');
INSERT INTO `user_borrow` VALUES (83, 1, 1, 1, 15, 0, '2023-05-29 00:00:00', '2023-05-30 00:00:00', '2023-05-28 00:00:00');
INSERT INTO `user_borrow` VALUES (84, 1, 1, 1, 15, 0, '2023-05-29 00:00:00', '2023-05-30 00:00:00', '2023-05-28 00:00:00');
INSERT INTO `user_borrow` VALUES (85, 1, 1, 1, 15, 0, '2023-05-29 00:00:00', '2023-05-30 00:00:00', '2023-05-28 00:00:00');
INSERT INTO `user_borrow` VALUES (86, 1, 1, 1, 15, 0, '2023-05-29 00:00:00', '2023-05-30 00:00:00', '2023-05-28 00:00:00');
INSERT INTO `user_borrow` VALUES (87, 1, 1, 1, 15, 0, '2023-05-29 00:00:00', '2023-05-30 00:00:00', '2023-05-28 16:58:23');
INSERT INTO `user_borrow` VALUES (88, 1, 3, 1, 15, 4, '2023-05-29 08:00:00', '2023-05-30 08:00:00', '2023-05-28 17:01:43');
INSERT INTO `user_borrow` VALUES (89, 2, 2, 1, 30, 4, '2023-05-29 08:00:00', '2023-05-30 08:00:00', '2023-05-28 17:05:51');
INSERT INTO `user_borrow` VALUES (90, 2, 2, 1, 30, 4, '2023-05-29 08:00:00', '2023-05-30 08:00:00', '2023-05-28 17:06:41');
INSERT INTO `user_borrow` VALUES (91, 2, 1, 1, 15, 4, '2023-05-29 08:00:00', '2023-05-30 08:00:00', '2023-05-28 19:32:56');
INSERT INTO `user_borrow` VALUES (92, 2, 1, 1, 15, 4, '2023-05-30 08:00:00', '2023-05-31 08:00:00', '2023-05-29 07:56:22');
INSERT INTO `user_borrow` VALUES (93, 2, 1, 1, 15, 0, '2023-05-30 00:00:00', '2023-05-31 00:00:00', '2023-05-29 08:02:21');
INSERT INTO `user_borrow` VALUES (94, 2, 2, 1, 30, 4, '2023-05-30 08:00:00', '2023-05-31 08:00:00', '2023-05-29 08:02:34');
INSERT INTO `user_borrow` VALUES (95, 2, 1, 2, 15, 3, '2023-05-31 08:00:00', '2023-06-01 08:00:00', '2023-05-29 10:00:31');

-- ----------------------------
-- Table structure for user_repairs
-- ----------------------------
DROP TABLE IF EXISTS `user_repairs`;
CREATE TABLE `user_repairs`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '报修编号',
  `repair_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报修原因',
  `borrowId` bigint(0) NOT NULL COMMENT '租用编号',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '报修状态，0-未修复，1-已修复',
  `created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `userId` bigint(0) NOT NULL COMMENT '报修用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_repairs_borrowId`(`borrowId`) USING BTREE,
  INDEX `FK_repairs_userId`(`userId`) USING BTREE,
  CONSTRAINT `FK_repairs_borrowId` FOREIGN KEY (`borrowId`) REFERENCES `user_borrow` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_repairs_userId` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_repairs
-- ----------------------------
INSERT INTO `user_repairs` VALUES (1, '球面有磨损', 5, 1, '2023-05-22 00:00:00', 1);
INSERT INTO `user_repairs` VALUES (2, '球没气', 5, 1, '2023-05-23 16:00:00', 1);
INSERT INTO `user_repairs` VALUES (3, '无', 5, 1, '2023-05-23 22:09:51', 1);
INSERT INTO `user_repairs` VALUES (4, '7777', 19, 1, '2023-05-26 08:35:23', 2);
INSERT INTO `user_repairs` VALUES (5, 'sdss', 88, 1, '2023-05-28 19:29:43', 1);
INSERT INTO `user_repairs` VALUES (6, '待发放', 94, 1, '2023-05-29 08:03:08', 2);

SET FOREIGN_KEY_CHECKS = 1;
