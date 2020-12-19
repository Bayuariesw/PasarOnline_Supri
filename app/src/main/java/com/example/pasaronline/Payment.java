package com.example.pasaronline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.PaymentMethod;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
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
                .setTransactionFinishedCallback(this)
                .setColorTheme(new CustomColorTheme("#777777", "#f77474", "#3f0d0d"))
                .buildSDK();
    }


    private void clickPay() {
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest("101", 20000, 1, "John"));
        MidtransSDK.getInstance().startPaymentUiFlow(this);
    }



    public static CustomerDetails customerDetails() {
        CustomerDetails cd = new CustomerDetails();
        cd.setFirstName("SUGENG");
        cd.setEmail("siswanto@gmail.com");
        cd.setPhone("0822222");
        return cd;
    }

    public static TransactionRequest transactionRequest(String id, int price, int qty, String name) {
        TransactionRequest request = new TransactionRequest(System.currentTimeMillis() + " ", 20000);
        request.setCustomerDetails(customerDetails());
        ItemDetails details = new ItemDetails(id, price, qty, name);

        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        itemDetails.add(details);
        request.setItemDetails(itemDetails);
        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_RBA);
        creditCard.setBank(BankType.BCA);

        request.setCreditCard(creditCard);
        return request;
    }


    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaksi Berhasil ID : " + result.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaksi Pending ID : " + result.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaksi Gagal ID : " + result.getResponse().getTransactionId(), Toast.LENGTH_SHORT).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaksi dibatalkan", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))) {
                Toast.makeText(this, "Transaksi gak valid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Ada yang salah nih", Toast.LENGTH_LONG).show();
            }

        }
    }
}


