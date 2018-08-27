set schema bookstore;

delete from t_book;

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1234-5678-1', '坊ちゃん', '夏目漱石','A出版社', 450 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1234-5679-2', '三四郎', '夏目漱石','A出版社', 480 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1234-5670-1', '走れメロス', '太宰治','A出版社', 580 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1234-5670-2', '富嶽百景', '太宰治','A出版社', 430 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1230-1234-1', '銀河鉄道の夜', '宮沢賢治','B出版社', 650 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1230-1234-2', 'セロ弾きのゴーシュ', '宮沢賢治','B出版社', 380 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1230-2345-1', '伊豆の踊り子', '川端康成','B出版社', 780 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-1230-2345-2', '雪国', '川端康成','B出版社', 700 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-3456-1111-1', '羅生門', '芥川龍之介','C出版社', 580 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-3456-1111-2', '蜘蛛の糸', '芥川龍之介','C出版社', 880 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-3456-2222-1', '大つごもり', '樋口一葉','C出版社', 550 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-3456-2222-2', 'たけくらべ', '樋口一葉','C出版社', 660 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-7980-1256-4', 'Javaフレームワーク入門', '掌田津耶乃','秀和システム',  2940 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-7980-1225-4', 'Eclipse3.1によるJavaアプリケーション開発', '水島和憲','秀和システム', 2940 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-7980-1209-2', 'はじめてのJSP/サーブレットプログラミング', 'アイティーブースト','秀和システム', 2835 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-7980-1187-8', 'Eclipse3+VisualEditorによるJavaプログラミング', 'プロジェクトウィルカ','秀和システム', 2625 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-7980-1086-3', 'UNIXコマンドリファレンス', '松本光春','秀和システム', 1365 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-7980-0680-7', '世界でいちばん簡単なUNIXのe本', '堀江幸生、山内敏昭','秀和システム', 1365 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-6543-1289-1', '世界の中心でUNIXを叫ぶ', '木村次郎','Z出版社', 2280 );

insert into t_book ( isbn, title, author, publisher, price ) values(
	'4-6543-2367-1', '今UNIXにゆきます', '鈴木三郎','Z出版社', 3800 );
