<?php
define("JAVA_HOSTS", "127.0.0.1:8080");
define("JAVA_SERVLET","/JavaBridge/JavaBridge.phpjavabridge");
require_once("Java.inc");

try{
    $key = '1234567812345678';
    $planData = '123';

    // 商户公钥证书序列号
    $signNo = "1052872704";
    // 证联公钥证书序列号
    $encrpNo = '1055490595';
    // 证联公钥文件路径
    $cert = '/Users/tian/2020/uhomes_console/house_spider/runtime/cert/UAT-ZLZF-1055490595.cer';
    // 商户私钥文件路径
    $sm2Cert = '/Users/tian/2020/uhomes_console/house_spider/runtime/cert/uat-B00000935-2.sm2';
    // 商户私钥证书密码
    $sm2Pwd = 'a111111';

    $sm4 = new Java('com.zlpay.assist.encrypt.sm4.SM4Util');
    $value = java_values($sm4->sm4EcbEncrypt($key, $planData, "NoPadding"));
    var_dump("sm4 ecb encrypt: $value");

    $helper = new Java('com.zlpay.javabridge.Helper');

    // sm4 encrypt
    $value = java_values($helper->encrypt($key,$planData));
    var_dump("sm4 encrypt: $value");

    // sm2 sign
    $data = '';
    $value = java_values($helper->sign($sm2Cert, $sm2Pwd, $signNo, $data));
    var_dump("sm2 sign: $value");

    // sm2 verify
    $sign = '';
    $data = '';
    //$value = java_values($helper->verify($cert, $encrpNo, $sign, $data));
    //var_dump("sm2 verify: $value");

    // sm4 decrypt
    $secret = '';
    $data = '';
    $value = java_values($helper->decrypt($sm2Cert,$sm2Pwd,$secret, $data));
    var_dump("sm4 decrypt: $value");

}catch(\Exception $ex){
    var_dump($ex->getMessage());
}

function getPublicKey($file)
{
    return file_get_contents($file);
}
