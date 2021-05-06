package sg.edu.rp.c346.id20011806.p03billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText amountInput;
        amountInput=findViewById(R.id.amountInput);
        EditText paxInput;
        paxInput=findViewById(R.id.PaxInput);
        ToggleButton svsToggle;
        svsToggle=findViewById(R.id.svsButton);
        ToggleButton gstToggle;
        gstToggle=findViewById(R.id.gstButton);
        EditText discountInput;
        discountInput = findViewById(R.id.discountInput);
        RadioGroup rgPayment;
        rgPayment=findViewById(R.id.radioGroupPayment);
        Button splitBtn;
        splitBtn=findViewById(R.id.splitButton);
        Button reset;
        reset=findViewById(R.id.resetButton);
        TextView totalBill;
        totalBill=findViewById(R.id.totalBillView);
        TextView splitAmt;
        splitAmt=findViewById(R.id.splitAmountView);

        splitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                double totalAmt = Double.parseDouble(amountInput.getText().toString());
                if (discountInput.getText().toString().trim().length() != 0) {
                    totalAmt *= 1 - Double.parseDouble(discountInput.getText().toString()) / 100;
                }

                if (amountInput.getText().toString().trim().length() !=0 && paxInput.getText().toString().trim().length() !=0) {
                    if (svsToggle.isChecked() && gstToggle.isChecked()) {
                        totalAmt *= 1.17;
                    } else if (svsToggle.isChecked() && !gstToggle.isChecked()) {
                        totalAmt *= 1.10;
                    } else if (!svsToggle.isChecked() && gstToggle.isChecked()) {
                        totalAmt *= 1.07;
                    }
                }

                int checkedRadioId = rgPayment.getCheckedRadioButtonId();
                String cashOption = "";
                String paynowOption = "";
                String splitCashOption = "";
                String splitPayNowOption = "";
                int paxNum = Integer.parseInt(paxInput.getText().toString());
                boolean isCash = false;

                if (checkedRadioId == R.id.radioButtonCash) {
                    isCash = true;
                    cashOption = String.format("$%.2f", totalAmt);
                    splitCashOption = String.format("$%.2f in cash", totalAmt/paxNum);
                    totalBill.setText("Total Bill: " + cashOption);
                } else if (checkedRadioId == R.id.radioButtonPayNow){
                    paynowOption = String.format("$%.2f", totalAmt);
                    splitPayNowOption = String.format("$%.2f via PayNow to 912345678", totalAmt/paxNum);
                    totalBill.setText("Total Bill: " + paynowOption);
                }

                if (paxNum > 1) {
                    if (isCash) {
                        splitAmt.setText("Each pays: " + splitCashOption);
                    } else if (!isCash){
                        splitAmt.setText("Each pays: " + splitPayNowOption);
                    }
                }

                reset.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        amountInput.setText("");
                        paxInput.setText("");
                        svsToggle.setChecked(false);
                        gstToggle.setChecked(false);
                        discountInput.setText("");
                        rgPayment.check(R.id.radioButtonCash);
                    }
                });

            }
        });

    }
}