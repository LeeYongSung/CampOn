package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Product;
import com.camp.campon.dto.Productreview;

public interface ProductService {

    //상품 전체 조회
    public List<Product> getProductList() throws Exception;
    //카테고리별 상품목록
    public List<Product> getCategoryList(String category) throws Exception;
    //상품 하나 선택 - 파일들
    public Product selectUpd(int productNo) throws Exception;
   //상품 하나 선택
   public Product select(int productNo) throws Exception;
    // 상품 등록
    public int productInsert(Product product) throws Exception;
    //상품 수정
    public int productUpdate(Product product) throws Exception;
  //상품 삭제
  public int deleteProduct(int productNo) throws Exception;
  //상품의 모든 후기 불러오기
  public List<Productreview> getReviewList() throws Exception;
    // 찜 목록
    public List<Product> wishList();

    // 찜 삭제
    public int wishListDelete(int wishlistNo);

    // 장바구니 목록
    public List<Product> cartList();

    // 장바구니 삭제
    public int cartListDelete(int cartlistNo);

    // 상품 상세페이지
    public List<Product> productimg(Integer productNo);

    // 상품번호에 따른 후기 불러오기
    public List<Productreview> getReviewListByNo(int productNo);

        // 리뷰 수
        public int reviewCount(int productNo);

         //장바구니 등록
    public int cartadd(int productNo, int userNo)  throws Exception;
}
