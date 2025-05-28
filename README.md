# BE10_4team_-
BE10_4team_책갈피연합

1. 개요
 
1.1 목적 및 배경 
본 프로젝트의 목적은 회원 전용 e-Book 전문 온라인 서점을 구현하는 것이다. 
이 시스템은 일반 사용자와 관리자를 위한 기능을 구분하여 제공하며, 사용자는 도서 
조회, 구매, 리뷰 등록 등의 서비스를 이용할 수 있고 관리자는 도서 및 회원 관리 기
능을 수행할 수 있다. 

해당 주제는 이전에 실습한 [Book] 클래스의 개념을 기반으로 확장한 것으로, 보다 
다양한 기능과 테이블 간의 관계를 다룸으로써 Java, JDBC, SQL 등 그동안 학습한 내
용을 실전에 적용할 수 있는 좋은 기회가 될 것이다.  

1.2 개발 환경

| 항목 | 내용 |
|---|:---:|
| 운영체제 | Windows 10 이상 |
| 개발 언어  | Java 8 이상 |
| DBMS | MySQL 8.0 |
| 개발 도구 | Eclipse |
| DB 설계 도구 | MySQL Workbench |
| JDBC 드라이버 | mysql-connector-java-8.0-xx.jar |

1.3 주제선정

e-book 회원 전문 온라인 서점(배달 사절)
  - 온라인으로 회원을 가입하여 e-book 판매로 운영하는 서점으로 코스트코 처럼 회원들에게만 판매 및 구입시 리워드 적립  

[요구사항정의서]

서점

[기본 기능]
    -고객이 상품(책)조회
    -고객이 상품(책)을 구매(결제)하기
	
[추가 기능]
    -고객이 상품을 검색하기
    -카테고리별 조회하기(SF,로맨스,공포)(List)
    -구매한 상품에 리뷰등록하기
    -잘팔린 책을 상위목록에 등록 or 추천한다
    -고객이 FAQ관련 게시글 조회하기

[구현기능]

유즈케이스
1.고객관리(User)
    -회원가입(insert)
    -회원정보수정(update)
    -회원탈퇴(delete)
    -로그인(select)
    -관리자 식별(true,false)
    [고객테이블]
    고객아이디,성명,가입날짜,주소,전화번호,이메일,포인트...
	
2.상품관리(Book)
    -상품목록을 조회(select * from book)
    -카테고리별 조회(select * from book where category=?)
    -상품명 조회(select * from book where book_name=?)
    (일부 매칭되면 검색되게)
        -상품의 상세보기
    -상품등록,수정,삭제(insert,update,delete)(관리자 전용)

3.리뷰관리
    -전체리뷰를 조회(상품별 리뷰)
    -리뷰등록,수정,삭제(insert,update,delete)

5.게시판관리
    -게시글목록보기
    -게시글상세조회
    -게시글등록,수정,삭제(관리자 전용)

6.결제관리
    -결제하기(책갈피페이,책갈피 포인트)
        -결제한 정보 조회하기



erDiagram
    direction LR

    USER {
        INT     id             PK "고객ID"
        VARCHAR name           "성명"
        DATETIME joined_at     "가입일"
        VARCHAR email          "이메일"
        VARCHAR password       "비밀번호"
        BOOLEAN is_admin       "관리자여부"
        INT     points         "포인트"
    }
	
    BOOK {
        INT     id             PK "상품번호"
        VARCHAR title          "상품명"
        VARCHAR category       "카테고리"
        DECIMAL price          "판매가"
        TEXT    description    "상품소개"
        VARCHAR author         "작가정보"
        INT     stock          "재고수량"
        INT     sales_count    "판매량"
    }
	
    ORDER {
        INT     id             PK "주문ID"
        INT     user_id        FK "고객ID"
        DATETIME order_date    "주문일시"
        VARCHAR status         "CART/PLACED"
        VARCHAR payment_method "결제수단"
        DECIMAL total_amount   "결제금액"
        INT     points_used    "사용포인트"
    }
	
    ORDER_ITEM {
        INT     id             PK "주문항목ID"
        INT     order_id       FK "주문ID"
        INT     book_id        FK "상품번호"
        INT     quantity       "수량"
    }
	
    REVIEW {
        INT     id             PK "리뷰ID"
        INT     user_id        FK "고객ID"
        INT     book_id        FK "상품번호"
        TINYINT rating         "별점(1~5)"
        TEXT    content        "리뷰내용"
        DATETIME created_at    "작성일시"
    }
	
    FAQ {
        INT     id             PK "게시글ID"
        VARCHAR title          "제목"
        TEXT    content        "내용"
        DATETIME created_at    "등록일시"
    }

    USER        --o{  ORDER        : "places"
    ORDER       --|{  ORDER_ITEM   : "contains"
    BOOK        --o{  ORDER_ITEM   : "included in"
    USER        --o{  REVIEW       : "writes"
    BOOK        --o{  REVIEW       : "has"
    USER        --o{  FAQ          : "posts"
