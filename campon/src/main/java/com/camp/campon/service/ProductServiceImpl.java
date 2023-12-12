package com.camp.campon.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.camp.campon.dto.Product;
import com.camp.campon.dto.Productreview;
import com.camp.campon.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public int productInsert(Product product) throws Exception {
        // * 썸네일 이미지
         List<MultipartFile> productThmFile =  product.getProductThmFile();
        if( !productThmFile.isEmpty() )
        for (MultipartFile file : productThmFile) {
        if( file.isEmpty() ) continue;
        String fileName = UUID.randomUUID().toString()+"_"+ file.getOriginalFilename();
        String filepath = uploadPath+"/"+fileName;
        File uploadfile = new File(uploadPath, fileName);
        byte[] filedata = file.getBytes();
        FileCopyUtils.copy(filedata, uploadfile);
        product.setProductThumnail(filepath);
        }
        // * 상품 상세 설명
        List<MultipartFile> productConFile =  product.getProductConFile();
        if( !productConFile.isEmpty() )
        for (MultipartFile file : productConFile) {
        if( file.isEmpty() ) continue;
        String fileName = UUID.randomUUID().toString()+"_"+ file.getOriginalFilename();
        String filepath = uploadPath+"/"+fileName;
        File uploadfile = new File(uploadPath, fileName);
        byte[] filedata = file.getBytes();
        FileCopyUtils.copy(filedata, uploadfile);
        product.setProductCon(filepath);
        }
        //product 테이블에 등록
        int result = productMapper.productInsert(product);
        log.info("상품등록여부 : "+result);
        int productMaxNo = productMapper.maxPk(); 
        log.info("상품번호 최댓값 : "+productMaxNo);
        product.setProductNo(productMaxNo);
        // * 상세이미지들
        List<MultipartFile> productImgs = product.getProductImgs();
        if( !productImgs.isEmpty() )
        for (MultipartFile file : productImgs) {
            if( file.isEmpty() ) continue;
            String originName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originName;
            String filePath = uploadPath + "/" + fileName;
            File uploadFile = new File(uploadPath, fileName);
            byte[] fileData = file.getBytes();
            FileCopyUtils.copy(fileData, uploadFile);
            //productimg 테이블에 정보 넣기
            product.setProductimgUrl(filePath); //productimgNo는 사실상 productNo와 같음. 
            productMapper.insertImgs(product);
        }
       
        return result;
    }

    @Override
    public int productUpdate(Product product) throws Exception {
        Product oldProduct = productMapper.select(product.getProductNo());
        // * 썸네일 이미지
        List<MultipartFile> productThmFile =  product.getProductThmFile();
        if( !productThmFile.isEmpty() )
        for (MultipartFile file : productThmFile) {
            log.info(""+file.isEmpty()); //true
            if( file.isEmpty() ) {
                    product.setProductThumnail(oldProduct.getProductThumnail());
            } else {
            String fileName = UUID.randomUUID().toString()+"_"+ file.getOriginalFilename();
            String filepath = uploadPath+"/"+fileName;
            File uploadfile = new File(uploadPath, fileName);
            byte[] filedata = file.getBytes();
            FileCopyUtils.copy(filedata, uploadfile);
            product.setProductThumnail(filepath);
        }
        }
        
        // * 상품 상세 설명
        List<MultipartFile> productConFile =  product.getProductConFile();
        for (MultipartFile file : productConFile) {
            if( file.isEmpty() ) {
                product.setProductCon(oldProduct.getProductCon());
            } else {
            String fileName = UUID.randomUUID().toString()+"_"+ file.getOriginalFilename();
            String filepath = uploadPath+"/"+fileName;
            File uploadfile = new File(uploadPath, fileName);
            byte[] filedata = file.getBytes();
            FileCopyUtils.copy(filedata, uploadfile);
            product.setProductCon(filepath);
            }
        }
        //product 테이블 수정
        int result = productMapper.productUpdate(product);
        log.info(product.toString());
        log.info("상품수정여부 : "+result);

        // * 상세이미지들
        List<MultipartFile> productImgs = product.getProductImgs();
        if( ! productImgs.get(0).isEmpty() ){
            int result2 = productMapper.deleteImgs(product.getProductNo());
            log.info("수정중인 productimg 테이블 삭제 여부 : " +result2);
            for (MultipartFile file : productImgs) {
                String originName = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originName;
                String filePath = uploadPath + "/" + fileName;
                File uploadFile = new File(uploadPath, fileName);
                byte[] fileData = file.getBytes();
                FileCopyUtils.copy(fileData, uploadFile);
                //productimg 테이블에 정보 넣기
                product.setProductimgUrl(filePath);
                int result3 = productMapper.insertImgs(product);
                log.info("수정중인 productimg 테이블 등록 여부 : " +result3);  
            }
        }
    
        return result;
    }

    @Override
    public List<Product> getCategoryList(String category) throws Exception {
        List<Product> productList = productMapper.getCategoryList(category);
        return productList;
    }

    @Override
    public List<Product> wishList(int userNo) throws Exception  {
        List<Product> wishlist = productMapper.wishList(userNo);
        return wishlist;
    }

    @Override
    public int wishListDelete(int wishlistNo) throws Exception  {
        int result = productMapper.wishListDelete(wishlistNo);
        return result;
    }

    @Override
    public Product select(int productNo) throws Exception {
        Product product = productMapper.select(productNo);
        return product;
    }

    @Override
    public List<Product> cartList(int userNo)  throws Exception {
        List<Product> cartlist = productMapper.cartList(userNo);
        return cartlist;
    }

    @Override
    public int cartListDelete(int cartlistNo) throws Exception  {
        int result = productMapper.cartListDelete(cartlistNo);
        return result;
    }

    public Product selectUpd(int productNo) throws Exception {
        Product product = productMapper.selectUpd(productNo);

        List<String> fileList = product.getProductImgsUrlList();

        for (String imgUrl : fileList) {
            log.info("imgUrl : " + imgUrl);
        }
        return product;
    }

    @Override
    public int deleteProduct(int productNo) throws Exception {
        int result1 = productMapper.deleteImgs(productNo);
        log.info("상세이미지들 삭제여부 : " + result1);
        int result = productMapper.deleteProduct(productNo);
        log.info("상품 삭제 여부 : "+result);
        return result;
    }

    @Override
    public List<Productreview> getReviewList() throws Exception {
        List<Productreview> productreviewList = productMapper.getReviewList();
        return productreviewList;
    }

    @Override
    public List<Product> getProductList() throws Exception {
        List<Product> productList = productMapper.getProductList();
        return productList;
    }
    @Override
    public List<Product> productimg(Integer productNo) throws Exception  {
        List<Product> productimg = productMapper.productimg(productNo);
        return productimg;
    }

    @Override
    public List<Productreview> getReviewListByNo(int productNo)  throws Exception {
        List<Productreview> getreviewlistbyno = productMapper.getReviewListByNo(productNo);
        return getreviewlistbyno;
    }
    @Override
    public List<Productreview> getReviewListByNoLim(int productNo)  throws Exception {
        List<Productreview> getReviewListByNoLim = productMapper.getReviewListByNoLim(productNo);
        return getReviewListByNoLim;
    }

    @Override
    public int reviewCount(int productNo)  throws Exception {
        int reviewCount = productMapper.reviewCount(productNo);
        return reviewCount;
    }

    @Override
    public int addCart(Product product)  throws Exception {
        int result = productMapper.addCart(product);
        return result;
    }

    @Override
    public int addCartAjax(Product product)  throws Exception {
        List<Product> oldcartList = productMapper.cartList(product.getUserNo());
        int result = 0;
        for (Product product2 : oldcartList) {
            if ( product2.getProductNo() == product.getProductNo() ){
                return result = 0;
            }
        }
        result = productMapper.addCart(product);
        return result;
    }



    @Override
    public int addcartAll(int userNo) throws Exception {
        int result = productMapper.addcartAll(userNo);
        return result;
    }

    //찜 등록
    @Override
    public int addProductsave(Product product) throws Exception {
        //만약 상품이 이미 찜에 등록되어있다면, 등록 안하고 넘어가야 함. 
        int userNo = product.getUserNo();
        List<Product> wishlist = productMapper.wishList(userNo);
        for (Product product2 : wishlist) {
            if ( product2.getProductNo() == product.getProductNo() ){
                return 0;
            }
        }
        int result = productMapper.addProductsave(product);
        return result;
    }

    @Override
    public List<Product> hotList() throws Exception {
        List<Product> productHotList = productMapper.hotList();
        return productHotList;
    }

    @Override
    public int cartUpdate(Product product) throws Exception {
        int result = productMapper.cartUpdate(product);
        return result;
    }

    @Override
    public List<Product> reservedProduct(int userNo) throws Exception {
        List<Product> productList = productMapper.reservedProduct(userNo);
        return productList;
    }

    @Override
    public List<Productreview> getReviewListLimit() throws Exception {
        List<Productreview> productreviewList = productMapper.getReviewListLimit();
        return productreviewList;
    }

    

}
