create database qy_shop;
use qy_shop;

create table qy_user_info(
	user_id INT(11) NOT NULL AUTO_INCREMENT COMMENT ''买家id'',
	user_nick varchar(30) NOT NULL DEFAULT '''' COMMENT ''买家名字'',
	user_password varchar(200) NOT NULL DEFAULT '''' COMMENT ''买家密码'',
	sex TINYINT(1) NOT NULL DEFAULT 0 COMMENT ''性别,0男，1女'',
	birth date NOT NULL DEFAULT ''0000-01-01'' COMMENT ''出生年月'',
	email varchar(40) NOT NULL DEFAULT '''' COMMENT ''邮箱'',
	phone varchar(13) NOT NULL DEFAULT 0 COMMENT ''手机号'',
	qq_num int(13) NOT NULL DEFAULT 0 COMMENT ''QQ号'',
	identity_card char(18) NOT NULL DEFAULT '''' COMMENT "身份证号",
	wechat_num varchar(60) NOT NULL DEFAULT '''' COMMENT ''微信号'',
	zfb_num varchar(60) NOT NULL DEFAULT '''' COMMENT ''支付宝账号'',
	create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
	update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
 PRIMARY KEY (`user_id`) USING BTREE,
 UNIQUE INDEX `user_nick`(`user_nick`) USING BTREE,
 UNIQUE INDEX `email`(`email`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=10000 CHARACTER SET = utf8mb4 COMMENT ''用户表'';

create table qy_category_info(
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `cid` bigint(11) NOT NULL COMMENT ''类目cid'',
  `name` varchar(64)  NOT NULL COMMENT ''类目名'',
  `parent_cid` bigint(11) not null DEFAULT 0 COMMENT ''父类目cid'',
  `is_parent` int(2) not null  DEFAULT 0 COMMENT ''是否为父类目，只有非父类目才能发布商品'',
  `cid_lev1` bigint(11) not null  DEFAULT 0 COMMENT ''一级类目cid'',
  `name_lev1` varchar(64)  not null  DEFAULT '''' COMMENT ''一级类目名'',
  `cid_lev2` bigint(11) not null  DEFAULT 0 COMMENT ''二级类目cid'',
  `name_lev2` varchar(64)  not null  DEFAULT '''' COMMENT ''二级类目名'',
  `cid_lev3` bigint(11) not null  DEFAULT 0 COMMENT ''三级类目cid'',
  `name_lev3` varchar(64)  not null  DEFAULT '''' COMMENT ''三级类目名'',
  `cid_lev4` bigint(11) not null  DEFAULT 0 COMMENT ''四级类目cid'',
  `name_lev4` varchar(64)  not null  DEFAULT '''' COMMENT ''四级类目名'',
  `cate_path` varchar(256)  not null  DEFAULT '''' COMMENT ''完整类目路径，用于前台显示，即所有级别的name用>隔开；例如：内衣>睡衣、家居服、睡袍、浴袍>睡衣、家居服'',
  `search_key` varchar(512)  not null  DEFAULT '''' COMMENT ''完整路径的类目名分词，将用于搜索，用英文逗号隔开（每个节点如果包含特殊字符“/”“_”“、”也需切分）切出来后重复的也要去掉；例如：cath_path=\"内衣>睡衣、家居服、睡袍、浴袍>睡衣、家居服\"==> search_key将切割组合为\"内衣,睡衣,家居服,睡袍,浴袍\" '',
  `used` int(11) not null  DEFAULT 0 COMMENT ''类目被使用次数，命中搜索后+1，预留，搜索结果列表显示的优先级条件'',
  `status` int(2) not null  DEFAULT 1 COMMENT ''状态，默认值1；可选值:1=normal(正常),0=deleted(删除)'',
  `sort_order` int(11) not null  DEFAULT 0 COMMENT ''排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'',
  `modified` datetime(0) not null  DEFAULT NOW() COMMENT ''最近修改时间'',
  `created` datetime(0) not null  DEFAULT NOW() COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_cid`(`cid`) USING BTREE,
  FULLTEXT INDEX `idx_so`(`search_key`)
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COMMENT ''类目信息表'';

create table qy_goods_info(
 gid INT(11) NOT NULL AUTO_INCREMENT COMMENT ''商品id'',
 gid_unique varchar(32) NOT NULL DEFAULT '''' COMMENT ''商品唯一标识'',
 sid INT(11) NOT NULL DEFAULT 0 COMMENT ''店铺ID商品所属店铺'',
 goods_cid bigint(19) NOT NULL DEFAULT 0 COMMENT ''宝贝叶子类目id'',
 goods_title varchar(100) NOT NULL DEFAULT '''' COMMENT ''商品标题'',
 goods_no varchar(100) NOT NULL DEFAULT '''' COMMENT ''商品货号'',
 goods_pic_url varchar(250) NOT NULL DEFAULT '''' COMMENT ''宝贝第一张主图的url'',
 goods_pic_url_long varchar(250) NOT NULL DEFAULT '''' COMMENT ''主图列表中的长图。即item_imgs中position=6'',
 goods_pic_url_list varchar(1500) NOT NULL DEFAULT '''' COMMENT ''宝贝主图列表, 英文逗号隔开'',
 goods_price DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT ''商品原价格'',
 goods_discount_price DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT ''商品折扣价'',
 goods_nums INT(11) NOT NULL DEFAULT 0 COMMENT ''商品总库存'',
 tag_colors varchar(300) NOT NULL DEFAULT '''' COMMENT ''颜色标签集'',
 tag_sizes varchar(300) NOT NULL DEFAULT '''' COMMENT ''尺寸标签集'',
 putaway_time datetime(0) NOT NULL DEFAULT NOW() COMMENT ''宝贝上架时间'',
 sold_out_time datetime(0) NOT NULL DEFAULT NOW() COMMENT ''宝贝下架时间'',
 goods_status  int(10) NOT NULL DEFAULT 0 COMMENT ''宝贝状态：1=正常上架，-1=正常下架（仓库中），-99=软删除'',
 is_enable_seckill int(3) NOT NULL DEFAULT 0 COMMENT ''是否开启秒杀,0不开启，1开启'',
 seckill_start_time datetime NULL default NULL COMMENT ''秒杀开始时间'',
 seckill_end_time datetime NULL default NULL COMMENT ''秒杀结束时间'',
 limit_buyer_count int(10) NOT NULL DEFAULT 0 COMMENT ''限购数'',
 create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
 update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
 PRIMARY KEY (`gid`) USING BTREE,
 UNIQUE INDEX `unique_gid`(`gid_unique`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000000 CHARACTER SET = utf8mb4 COMMENT ''商品表'';

create table qy_goods_details (
id int(11) NOT NULL AUTO_INCREMENT,
gid int(11) NOT NULL DEFAULT 0 COMMENT ''商品ID'',
gid_unique varchar(32) NOT NULL DEFAULT '''' COMMENT ''商品唯一标识'',
sku_json json NULL DEFAULT NULL COMMENT ''商品sku信息'',
props_names varchar(2000) NOT NULL DEFAULT '''' COMMENT ''商品属性'',
input_pids varchar(300) NOT NULL DEFAULT ''''  COMMENT  ''自定义输入的属性pid'',
input_str varchar(1000) NOT NULL DEFAULT '''' COMMENT ''自定义输入的属性字符串'',
goods_desc  mediumtext NULL DEFAULT null COMMENT ''商品详情'',
create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
PRIMARY KEY (`id`) USING BTREE,
UNIQUE INDEX `gid`(`gid`) USING BTREE,
UNIQUE INDEX `unique_gid`(`gid_unique`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT ''商品详情表'';

CREATE TABLE qy_areas(
id int(11) NOT NULL AUTO_INCREMENT,
parent_id int(10) NOT NULL DEFAULT 0 COMMENT ''父ID'',
area_code int(10) NOT NULL DEFAULT 0 COMMENT ''地区code'',
area_name VARCHAR(100) NOT NULL DEFAULT '''' COMMENT ''地区名称'',
is_show tinyint(4) NOT NULL default 0 COMMENT ''是否显示	0：是 1：否'',
area_sort INT(11) NOT NULL DEFAULT 0 COMMENT ''排序号'',
area_key	VARCHAR(10) NOT NULL DEFAULT '''' COMMENT ''地区首字母'',
area_type TINYINT(4) NOT NULL DEFAULT 1 COMMENT ''级别标志	1：省 2：市 3：县区 4: 街道'',
status TINYINT(4) NOT NULL DEFAULT 0 COMMENT ''启用：0  禁用：1'',
create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
 PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  INDEX `area_code`(`area_code`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 comment ''城市地区表'';

create table qy_delivery_address(
address_id	int(11) NOT NULL AUTO_INCREMENT,
user_id	int(11)	NOT NULL DEFAULT 0 COMMENT ''用户id'',
user_name	varchar(50) NOT NULL DEFAULT '''' COMMENT ''收货人名称'',
user_phone	VARCHAR(20) NOT NULL DEFAULT '''' COMMENT ''收货人手机号码'',
area_id_path	VARCHAR(255) NOT NULL DEFAULT '''' COMMENT ''区域ID路径	省Id_市Id_县Id 例如:440000_440100_440106'',
area_id	INT(11) NOT NULL DEFAULT 0 COMMENT ''最后一级区域ID'',
user_address VARCHAR(255) NOT NULL DEFAULT '''' COMMENT ''详细地址'',
is_default	TINYINT(4) NOT NULL DEFAULT 1 COMMENT ''是否默认地址	0：否 1：是'',
data_flag	TINYINT(4) NOT NULL DEFAULT 1 COMMENT ''有效状态	1：有效 -1：无效'',
create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
 PRIMARY KEY (`address_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 comment ''收货地址表'';

create table qy_order_info(
id INT(11) not null auto_increment,
order_no varchar(60) NOT NULL DEFAULT '''' COMMENT ''订单号'',
user_id int(11) NOT NULL DEFAULT 0 COMMENT ''买家ID'',
deliver_money  DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT ''运费'',
total_money DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT ''订单总金额,包括运费'',
real_total_money DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT ''实际订单总金额	,进行各种折扣之后的金额'',
goods_list json NULL DEFAULT NULL COMMENT ''购买的商品列表'',
order_status tinyint(4) NOT NULL DEFAULT 0 COMMENT ''-3：用户拒收 -2：未付款的订单 -1：用户取消 0：待发货 1：配送中 2：用户确认收货'',
pay_type TINYINT(4) NOT NULL DEFAULT 0 COMMENT ''支付方式	0：在线支付,1:货到付款'',
pay_from TINYINT(4)	NOT NULL DEFAULT 0 COMMENT ''支付来源	1：支付宝 2：微信'',
is_pay TINYINT(4) NOT NULL DEFAULT 0 COMMENT ''是否支付	0：未支付 1：已支付'',
area_id	int(11) NOT NULL COMMENT ''最后一级区域ID'',
area_id_path varchar(255) NULL COMMENT ''区域ID路径	省级id_市级id_县级Id_ 例如:110000_110100_110101_'',
user_name	varchar(20) NOT NULL COMMENT ''收货人名称'',
user_address varchar(255) NOT NULL COMMENT ''收件人地址'',
user_phone char(11) NOT NULL COMMENT ''收件人手机号'',
invoice_client varchar(255) NULL COMMENT ''发票抬头'',
order_score int(11) NOT NULL DEFAULT 0 COMMENT ''所得积分'',
is_invoice TINYINT(4) NOT NULL DEFAULT 0 COMMENT ''是否需要发票,0不需要，1需要'',
order_remark varchar(255) NOT NULL DEFAULT '''' COMMENT ''订单备注'',
order_src tinyint(4) NOT NULL DEFAULT 0 COMMENT ''订单来源'',
receive_time datetime NULL DEFAULT NULL COMMENT ''收货时间'',
cancel_reason varchar(255) NOT NULL DEFAULT '''' COMMENT ''关闭原因'',
reject_other_reason	varchar(255) NOT NULL DEFAULT '''' COMMENT ''拒收原因'',
is_closed TINYINT(4) NOT NULL DEFAULT 0 COMMENT	''是否订单已完结	0：未完结 1：已完结'',
express_id int(11) NULL COMMENT ''快递公司'',
express_no varchar(20) NULL COMMENT ''快递号'',
trade_no varchar(100) NULL COMMENT ''在线支付流水'',
create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
 PRIMARY KEY (`id`) USING BTREE,
 UNIQUE INDEX `unique_order_no`(`order_no`) USING BTREE,
 INDEX `idx_user_id`(`user_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT ''订单表'';

create table qy_trade_info(
id INT(11) NOT NULL AUTO_INCREMENT,
trade_id varchar(100) NOT NULL DEFAULT '''' COMMENT ''交易支付编号，通常为第三方生成的ID'',
order_no varchar(60) NOT NULL DEFAULT '''' COMMENT ''对应的订单号'',
trade_type int(10) NOT NULL DEFAULT 0 COMMENT ''交易支付类型，0支付宝，1微信，2网银'',
trade_info json NULL DEFAULT NULL COMMENT ''交易信息'',
create_at datetime NOT NULL DEFAULT NOW() COMMENT ''创建时间'',
update_at datetime NOT NULL DEFAULT NOW() COMMENT ''修改时间'',
 PRIMARY KEY (`id`) USING BTREE,
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT ''交易信息表'';