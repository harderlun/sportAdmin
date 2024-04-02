module.exports = { // placeone
	PROJECT_COLOR: '#E285AA',
	NAV_COLOR: '#000000',
	NAV_BG: '#E285AA',

	// setup
	SETUP_CONTENT_ITEMS: [
		{ title: '关于我们', key: 'SETUP_CONTENT_ABOUT' },
	],

	// 用户
	USER_REG_CHECK: false,
	USER_FIELDS: [
	],

	NEWS_NAME: '最新通知',
	NEWS_CATE: [
		{ id: 1, title: '最新通知', style: 'leftpic' },
	],
	NEWS_FIELDS: [

	],

	ENROLL_NAME: '场地',
	ENROLL_CATE: [
		{ id: '1', title: '场地' },
		{ id: '2', title: '器材' },
		{ id: '3', title: '比赛' },
	],
	ENROLL_FIELDS: [


	],
	ENROLL_JOIN_FIELDS: [
		{ mark: 'name', type: 'text', title: '姓名', must: true, min: 2, max: 30, edit: false },
		{ mark: 'phone', type: 'text', len: 11, title: '手机号', must: true, edit: false },
	],

	ENROLL_TIME_NODE: {
		mark: 'mark-no',
		start: 9 * 2, // 开始   48进制
		end: 22 * 2 + 1, // 结束
		price: 20, //价格 
		succ: false //是否已预订 
	},
	ENROLL_DAY_NODE: [
		{
			mark: 'mark-no',
			start: 9 * 2, //开始 48进制
			end: 21 * 2 + 1, // 结束
			price: 20,
			succ: false
		},
	],

 
}