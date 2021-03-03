# 开发文档

App开发文档

**注意事项**

- 使用java语言编写，不能使用Kotlin
- 尽量控制包的大小，最好在5M以内
- Adjust、埋点与支付平台暂不需要对接
- 不要有中文控件，中文提示
- minSdkVersion 21,targetSdkVersion 28以上
- 用androidx
- 打包之前混淆的文件要写好
- 界面上电话号码前“+91”写死
- 要严格按照接口文档写的post get请求，post请求的请求内容要是json格式，get拼路径，接口已经经过验证的，基本没问题，请求不了或者接口返回错误先检查下是不是传的内容有问题
- 支付页，金额选择界面调用的是product接口，最上面的amount选择控件使用product.limits的amount数据,limits下面还有个durations的list,durations展示term的数据，amount跟term是联动的，amount选择一个值 term也要跟着变，以及下面列表的数据都是要跟着变得(列表使用durations这个集合里面的数据，参照返回值一一对应) ，各字段按照以下一一对应  duration -> Loan term 、interest -> Loan Interest、monthly_payment ->  Monthly Payment 、monthly_principal -> Monthly principal 、  monthly_inerest ->  Monthly inerest 、 Bank Card Number使用profile接口里面的bank_account_no 、 member fee里面划线的金额用member_ori_fee、 没划线得到金额用member_fee。
- 设计里有包含“ 982****261 Today has been the success of the loan amount”类似文案的，为上下轮播展示，请调用接口marquee获取数据。

 

 

**接口**

- http状态码401 表示未登录或者登录超时，请跳转登录页
- 接口返回的 status 字段用户判断接口请求是否正确，status=1表示请求正确，status=0
  表示请求错误，通常是因为参数异常或逻辑异常导致，请直接toast message里边的内容即可。

 


**启动页**
启动页需要强制调用/config接口，加载必要的系统参数以及相应的数据字典，保存在本地，后续直接从本地获取使用。
如果接口调用失败，则弹出对话框让用户重试，直到成功，再进行下一步逻辑。
具体参数以及说明请参考接口文档

 


**登录页**
本App为强制登录模式，必须登录才能进入首页，测试环境默认验证码为8888

 


**首页**

- 首页需要调用/product
  接口，具体参数请参考接口文档。
- 首页需要做下拉刷新，以及每次首页展示的时候，都需要调用/product
  接口来刷新状态。
- 首页按钮的跳转逻辑，依据接口返回的phase字段与certification字段来综合判断
- 从首页进入支付页面添加如下判断
  if(phase = 2){
   先判断当前手机号码是否为本地缓存中已经展示过的，如果展示过，就直接进入支付页面，否则先进入验证成功界面
             }                   【20201106新增】


**phase字段**

- 0: 用户未认证。跳转认证页，依据certification的值来判断具体跳转哪一个认证页
- 1: 审核中。跳转审核中页。
- 2: 审核通过。跳转审核通过页或支付页，判断逻辑为，审核通过页仅展示一次(需本地记录是否曾经展示过)，之后均跳转支付页。
- 3: 已付款。用户已付款，首页直接展示贷款产品页。

 

**certification字段**

当phase字段为0时，需要根据该字段来判断具体跳转哪一个认证页

- 0: 认证完成。当phase字段为0时，该字段不可能为
  0
- 1: 跳转Base Info页
- 2: 跳转Work Info页
- 3: 跳转Bank Info页


**补充说明**

- 首页实际包含两个页面，用户未付款(phase<=2)以及用户已付款(phase==3)，分别展示产品展示以及贷超列表页。


 

**认证页**

- 认证页顺序依次为Base Info页->Work Info页->Bank Info页面，当前页面提交后，跳转下一个认证页。
- 当最后一个Bank Info认证页完成后，跳转审核中页。
- 认证页包含的一些下拉选项的键值，在启动页已经存在本地，这里从本地拉取。
- 个人中心也包含认证页的入口，即资料修改，这里与认证页类似，但逻辑上略有不同，这里提交后，不需要跳转至下一个
  认证页。

 


**审核中页**

- 这里需要每3s循环调用/product页，查看phase状态，当phase>=2时，跳转审核成功页
- 页面下方的文案，在启动页里调用的/config接口中有返回。

 

 

**审核成功页**

- 在首页的说明里已经提到，**该页面仅展示一次**，以后每次点击首页主按钮，phase==2时，不再跳转该页面，而是跳转支付页。/config接口中有返回。
- 该页面点击主按钮，下一步跳转支付页
- 页面下方的文案，在启动页里调用的
- 认证成功界面展示逻辑：如果流程中出现认证成功界面，将访问记录缓存到本地，标记已展示 【20201106新增】

 


**支付页(写完页面即可，暂不用对接支付平台)**

- 支付页需要的相关参数在首页的/product接口中的limits字段有返回，具体字段明细说明请参考接口文档。
- 需要对接两种支付平台，分别是razorpay与cashfree，在该页具体采用哪种支付方式，在启动页调用/config接口的pay_channel字段有返回。
- 依据不同的支付，调用不同的接口来创建订单。razorpay与cashfree分别为/razorpay/create与cashfree/create接口。
- 支付成功直接返回首页即可。

 


**个人中心**

- 个人中心相关页面逻辑比较简单，这里不再做详细说明。
- 客服相关参数在启动页/config接口里都有返回。
- **请增加一个逻辑，在个人中心底部，用config接口返回的 sys_service_email_bak 字段，展示客服邮箱，如果该字段为空，则不展示。**【20201107新增】

 


**埋点(写完页面即可，暂不用对接埋点)**
接口/track/event,参数包含type以及loanid,loanid仅在贷款超市页，点击具体贷款产品的时候，需要赋值。

|   **type**    | **loan_id** |                  **时机**                   |
| :-----------: | :---------: | :-----------------------------------------: |
|    install    |    null     | 用户安装APP，写在googleAdId获取成功的回调里 |
|   register    |    null     |                用户注册成功                 |
|     login     |    null     |                用户登录成功                 |
| add_basicinfo |    null     |           用户添加Basic Info成功            |
| add_workinfo  |    null     |            用户添加Work Info成功            |
| add_bankinfo  |    null     |            用户添加Bank Info成功            |
|   click_pay   |    null     |              用户点击支付按钮               |
|  pay_success  |    null     |   用户支付成功，写在成功支付的回调方法里    |
|     loan      |   loan_id   |            用户点击贷款超市产品             |

 

 

**补充说明**

- 用户登录接口返回的action字段，有标明当前用户是注册还是登录；如果依据接口判断当前用户是注册，那仍需要做一次login的埋点。
- 也就是说，当用户第一次注册，需要埋点register以及login；当用户之前已经注册，而只是登录，则只需要埋点login


**请求头**
newBuilder.header("Content-Type", "application/json")
      .header("os-type", "android")
      .header("os-version", Build.VERSION.RELEASE.trim())   //ANDROID版本
      .header("brand", Build.BRAND.trim())               //手机品牌
      .header("model", Build.MODEL.trim())              //手机型号
      .header("app-version", appVersion)                //当前App版本号
      .header("device-id", deviceId)                    //设备唯一ID，APP初始化时，自行生成一个UUID存储本地，UUID.r

​      .header("adid", adjustadid)                       //这里留空，我们来处理

​      .header("gps-adid", gpsadid)                     //这里留空，我们来处理

​      .header("token", token);                         //用户登录接口返回的用户token

 


**支付平台对接**

- **Cashfree对接文档: https://dev.cashfree.com/payment-gateway/integrations/mobile-integration/android-sdk**
- **Cashfree拉起支付代码**

​        private void doCashFree(ResCashFree resCashFree, int position) {
​            Map<String, String> params = new HashMap<>();
​            orderId = resCashFree.orderId;
​            params.put(PARAM_APP_ID, resCashFree.appId);
​            params.put(PARAM_ORDER_ID, orderId);
​            params.put(PARAM_ORDER_AMOUNT, resCashFree.amount + "");
​            params.put(PARAM_CUSTOMER_PHONE, UserManager.getInstance().mobile);
​            params.put(PARAM_CUSTOMER_EMAIL, resCashFree.email);
​            params.put(PARAM_NOTIFY_URL, resCashFree.notifyUrl);
​            CFPaymentService cfPaymentService =CFPaymentService.getCFPaymentServiceInstance();
​            cfPaymentService.setOrientation(0);
​            showDialog();
​            /**
​            \* Cashfree支付包含两种支付方式，需要从下部弹出选项卡让用户选择，
​            \* UPI & Pay
​            \* Card,Wallets,Net Banking & Pay
​            */


            if (position == 0) {
              // UPI & Pay
              cfPaymentService.upiPayment(GetLoanActivity.this, params, resCashFree.cftoken, "PROD");
            } else {
               // Card,Wallets,Net Banking & Pay
               cfPaymentService.doPayment(GetLoanActivity.this, params, resCashFree.cftoken, "PROD", "#a31aac", "#FFFFF
             }
}

 

 

- **Cashfree回调代码**

​        //public class MerchantActivity extends Activity implements PaymentResultWithDataListener
​        @Override
​        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
​            super.onActivityResult(requestCode, resultCode, data);
​            dismissDialog();
​            if (data != null) {
​              final Bundle bundle = data.getExtras();
​              if (bundle != null)
​                 for (String key : bundle.keySet()) {
​                    if (bundle.getString(key) != null) {
​                      Log.d("xjl", key + ":" + bundle.getString(key));
​                      if (bundle.getString("txStatus").equals("FAILED") && !TextUtils.isEmpty(bundle.getString("tx
​                        Log.d("xjl", "FAILED");
​                      }

​                      if (key.equals("txStatus") &&(bundle.getString("txStatus").equals("SUCCESS") || bundle.getS
​                      }
​                     }
​                   }
​               }
​             }

 

 

- **Razorpay对接文档: https://razorpay.com/docs/payment-gateway/android-integration/standard**
- **Razorpay拉起支付代码**

​       public void startPayment() {
​       checkout.setKeyID("<YOUR_KEY_ID>");
​       Checkout checkout = new Checkout();
​       checkout.setImage(R.drawable.logo);
​       final Activity activity = this;
​          try {
​          JSONObject options = new JSONObject();
​          options.put("name", "Merchant Name");
​          options.put("description", "Reference No. #123456");
​          options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
​          options.put("theme.color", "#3399cc");
​          options.put("currency", "INR");
​          options.put("amount", "50000");//pass amount in currency subunits
​          options.put("prefill.email", "gaurav.kumar@example.com");
​          options.put("prefill.contact","9988776655");
​          checkout.open(activity, options);
​          } catch(Exception e) {
​             Log.e(TAG, "Error in starting Razorpay Checkout", e);
​          }
​        }

 

- **Razorpay回调代码**

   public class MerchantActivity extends Activity implements PaymentResultListener {
       @Override
        public void onPaymentSuccess(String razorpayPaymentID) {
           /**
            \* Add your logic here for a successful payment response
            */
        }

​        @Override
​        public void onPaymentError(int code, String response) {
​            /**
​             \* Add your logic here for a failed payment response
​             */
​         }
​      }
 


 