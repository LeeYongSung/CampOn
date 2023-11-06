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
        if (product.getProductThmFile() == null || product.getProductThmFile().size() == 0 ){
            product.setProductThumnail(oldProduct.getProductThumnail());
        } else {
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
        }
        // * 상품 상세 설명
        if (product.getProductConFile() == null || product.getProductConFile().size() == 0 ){
            product.setProductCon(oldProduct.getProductCon());
        } else {
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
        }
        //product 테이블 수정
        int result = productMapper.productUpdate(product);
        log.info("상품수정여부 : "+result);
        // * 상세이미지들
         if (product.getProductImgs() == null || product.getProductImgs().size() == 0 ){
            
        } else {
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
                product.setProductimgUrl(filePath);
                int result2 = productMapper.deleteImgs(product.getProductNo());
                int result3 = productMapper.insertImgs(product);
                log.info("수정중인 productimg 테이블 삭제 여부 : " +result2);
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
    public List<Product> wishList() {
        List<Product> wishlist = productMapper.wishList();
        return wishlist;
    }

    @Override
    public int wishListDelete(int wishlistNo) {
        int result = productMapper.wishListDelete(wishlistNo);
        return result;
    }

    @Override
    public Product select(int productNo) throws Exception {
        Product product = productMapper.select(productNo);
        return product;
    }

    @Override
    public List<Product> cartList() {
        List<Product> cartlist = productMapper.cartList();
        return cartlist;
    }

    @Override
    public int cartListDelete(int cartlistNo) {
        int result = productMapper.cartListDelete(cartlistNo);
        return result;
    }

    @Override
    public List<Product> productimg(Integer productNo) {
        List<Product> productimg = productMapper.productimg(productNo);
        return productimg;
    }

    

    
}
