package com.camp.campon.service;

import java.util.List;

import com.camp.campon.dto.Product;
import com.camp.campon.dto.Productreview;

public interface ProductService {

  // 상품 전체 조회
  public List<Product> getProductList() throws Exception;

  // 카테고리별 상품목록
  public List<Product> getCategoryList(String category) throws Exception;

  // 상품 하나 선택 - 파일들
  public Product selectUpd(int productNo) throws Exception;

  // 상품 하나 선택
  public Product select(int productNo) throws Exception;

  // 상품 등록
  public int productInsert(Product product) throws Exception;

  // 상품 수정
  public int productUpdate(Product product) throws Exception;

  // 상품 삭제
  public int deleteProduct(int productNo) throws Exception;

  // 상품의 모든 후기 불러오기
  public List<Productreview> getReviewList() throws Exception;

  //상품후기들을 5개만 가져오기 (최신순으로)
  public List<Productreview> getReviewListLimit() throws Exception;

  // 찜 목록
  public List<Product> wishList(int userNo) throws Exception;
  //찜 등록
  public int addProductsave(Product product) throws Exception;
  // 찜 삭제
  public int wishListDelete(int wishlistNo) throws Exception;
   // 찜목록 전부다 장바구니에 담기
   public int addcartAll(int userNo) throws Exception;

  // 장바구니 목록
  public List<Product> cartList(int userNo) throws Exception;
  //장바구니 업뎃
  public int cartUpdate(Product product) throws Exception;
  //장바구니 업뎃 ajax
  public int addCartAjax(Product product)  throws Exception ;
  // 장바구니 삭제
  public int cartListDelete(int cartlistNo) throws Exception;

  // 상품 상세페이지
  public List<Product> productimg(Integer productNo) throws Exception;

  // 상품번호에 따른 후기 불러오기
  public List<Productreview> getReviewListByNo(int productNo) throws Exception;
  // 상품번호에 따른 후기 불러오기 (5개 제한)
  public List<Productreview> getReviewListByNoLim(int productNo) throws Exception;

  // 리뷰 수
  public int reviewCount(int productNo) throws Exception;

  // 장바구니 등록
  public int addCart(Product product) throws Exception;

  // 추천상품 목록
  public List<Product> hotList() throws Exception;
   //유저번호에 따른 대여된 상품들
   public List<Product> reservedProduct(int userNo) throws Exception;
}
