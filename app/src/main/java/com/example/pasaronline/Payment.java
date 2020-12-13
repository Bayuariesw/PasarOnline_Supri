package com.example.pasaronline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;


public class Payment extends AppCompatActivity implements TransactionFinishedCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.btn_bayar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPay();
            }
        });
        makePayment();
    }

    public void makePayment() {
        SdkUIFlowBuilder.init()
                .setContext(this)
                .setClientKey(BuildConfig.MERCHANT_CLIENT_KEY)
                .setMerchantBaseUrl(BuildConfig.MERCHANT_BASE_URL)
                .enableLog(true)
                .setColorTheme(new CustomColorTheme("#777777", "#f77474", "#3f0d0d"))
                .buildSDK();
    }


    private void clickPay() {
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("101", 2000, 1, "John"));
        MidtransSDK.getInstance().startPaymentUiFlow(Payment.this);
    }

    public static CustomerDetails customerDetails() {
        CustomerDetails cd = new CustomerDetails();
        cd.setFirstName("NAMAMU");
        cd.setLastName("belakang");
        cd.setEmail("email@gmail.com");
        cd.setPhone("Nope");
        return cd;
    }

    public static TransactionRequest transactionRequest(String id, int price, int qty, String name) {
        TransactionRequest request = new TransactionRequest(System.currentTimeMillis() + " ", 2000);
        request.setCustomerDetails(customerDetails());
        ItemDetails details = new ItemDetails(id, price, qty, name);

        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        itemDetails.add(details);
        request.setItemDetails(itemDetails);
        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
//        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_RBA);

        request.setCreditCard(creditCard);
        return request;
    }


    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Sukses " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Failed", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))) {
                Toast.makeText(this, "Transaction Invalid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_LONG).show();
            }

        }
    }
}


