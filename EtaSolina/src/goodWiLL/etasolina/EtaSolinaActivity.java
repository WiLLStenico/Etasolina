package goodWiLL.etasolina;

import whs.library.PricePicker;
import whs.library.PricePicker.OnPricePickerChangedListener;
import whs.library.utils.Utils;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EtaSolinaActivity extends Activity implements
		OnPricePickerChangedListener {
	private PricePicker pricePickerEtanol;
	private PricePicker pricePickerGasolina;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pricePickerEtanol = (PricePicker) findViewById(R.id.pricePickerEtanol);
		pricePickerGasolina = (PricePicker) findViewById(R.id.pricePickerGasolina);

		pricePickerGasolina.setOnPricePickerChangeListener(this);
		pricePickerEtanol.setOnPricePickerChangeListener(this);

		ImageView iv_PricePickerEtanol = (ImageView) ((ViewGroup) pricePickerEtanol
				.getChildAt(0)).getChildAt(1);
		iv_PricePickerEtanol.setImageResource(R.drawable.im_etanol);

		ImageView iv_PricePickerGasolina = (ImageView) ((ViewGroup) pricePickerGasolina
				.getChildAt(0)).getChildAt(1);
		iv_PricePickerGasolina.setImageResource(R.drawable.im_gasolina);

		getPreferences();

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * whs.library.PricePicker.OnPricePickerChangedListener#onPricePickerChanged
	 * (whs.library.PricePicker, int, int)
	 */
	public void onPricePickerChanged(PricePicker picker, int oldVal, int newVal) {
		calculaResultado();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		savePreferences();

		Utils.unbindDrawables(findViewById(R.id.mainLayout));
		System.gc();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		savePreferences();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		getPreferences();
	}

	private void getPreferences() {
		// Recupera o valor do contador, salvo nas preferencias
		SharedPreferences savedPrices = getSharedPreferences("PRICES",
				MODE_PRIVATE);

		// O segundo argumento é o valor default se não encontrar
		pricePickerGasolina.setCurrent(savedPrices.getFloat("gasolina", 0));
		pricePickerEtanol.setCurrent(savedPrices.getFloat("etanol", 0));
	}

	private void savePreferences() {
		// Salva o contador nas preferências
		SharedPreferences savedPrices = getSharedPreferences("PRICES",
				MODE_PRIVATE);

		// Abre a preferência para edição
		SharedPreferences.Editor editor = savedPrices.edit();

		// Atualiza o valor do contador
		editor.putFloat("gasolina", pricePickerGasolina.getCurrent());
		editor.putFloat("etanol", pricePickerEtanol.getCurrent());

		Log.i("EtaSolina", "Status salvo");

		// Faz commit para salvar os dados
		editor.commit();
		calculaResultado();
	}

	private void calculaResultado() {

		float valorGasolina = pricePickerGasolina.getCurrent();
		float valorEtanol = pricePickerEtanol.getCurrent();
		
		LinearLayout llResultado = (LinearLayout) findViewById(R.id.ll_resultado);
		TextView tvResult = (TextView) findViewById(R.id.tv_Result);
		

		if (valorEtanol <= valorGasolina * 0.7f) {									
			llResultado.setBackgroundResource(R.drawable.iv_use_etanol);
			tvResult.setText("Use Etanol");
		} else {
			llResultado.setBackgroundResource(R.drawable.iv_use_gasolina);
			tvResult.setText("Use Gasolina");
		}
	}

}