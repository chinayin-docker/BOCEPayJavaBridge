<?php
define("JAVA_HOSTS", "127.0.0.1:8080");
define("JAVA_SERVLET","/JavaBridge/JavaBridge.phpjavabridge");
require_once("Java.inc");

try{
    $System = java("java.lang.System");
    echo $System->getProperties().PHP_EOL;

    $planData = '123';
    $pkcs7 = new Java('com.bocnet.common.security.PKCS7Tool');
    $sign = $pkcs7->getSigner('/app/cert.pfx', "123", "123");
    $signData = java_values($sign->sign(getBytes($planData)));
    print_r($signData);

}catch(\Exception $ex){
    print_r($ex->getMessage());
}

//获取byte[]的原文 用来加签
function getBytes($string) {
  $bytes = array();
  for($i = 0; $i < strlen($string); $i++){
    $bytes[] = ord($string[$i]);
  }
  return $bytes;
}
