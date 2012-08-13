/**
 * 
 */
package whs.library;

import whs.library.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * @author WiLL
 * 
 */
public class PricePicker extends LinearLayout implements
		whs.library.NumberPicker.OnChangedListener {

	private NumberPicker numberPickerReal;
	private NumberPicker numberPickerDezenaCentavo;
	private NumberPicker numberPickerCentavo;

	public interface OnPricePickerChangedListener {
		void onPricePickerChanged(PricePicker picker, int oldVal, int newVal);
	}

	private OnPricePickerChangedListener mListener;

	/**
	 * @param context
	 */
	public PricePicker(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PricePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setOrientation(HORIZONTAL);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.price_picker, this, true);

		numberPickerReal = (NumberPicker) findViewById(R.id.precoReal);
		numberPickerDezenaCentavo = (NumberPicker) findViewById(R.id.precoDezenaCentavo);
		numberPickerCentavo = (NumberPicker) findViewById(R.id.precoCentavo);

		numberPickerReal.setOnChangeListener(this);
		numberPickerDezenaCentavo.setOnChangeListener(this);
		numberPickerCentavo.setOnChangeListener(this);

		numberPickerReal.setRange(0, 99);
		numberPickerDezenaCentavo.setRange(0, 9);

		numberPickerCentavo.setRange(0, 9);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * whs.library.NumberPicker.OnChangedListener#onChanged(whs.library.NumberPicker
	 * , int, int)
	 */
	public void onChanged(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onPricePickerChanged(this, oldVal, newVal);
		}
	}

	public void setOnPricePickerChangeListener(
			OnPricePickerChangedListener listener) {
		mListener = listener;
	}

	/**
	 * @return the current value.
	 */
	public float getCurrent() {
		return (float) numberPickerReal.getCurrent()
				+ (float) numberPickerDezenaCentavo.getCurrent() * (float) 0.1
				+ (float) numberPickerCentavo.getCurrent() * (float) 0.01;
	}

	/**
	 * @return the current value.
	 */
	public void setCurrent(float currentValue) {

		int real = (int) currentValue;
		int dezenaCentavos = (int) ((currentValue * 10) - (real * 10));
		int centavos = (int) ((currentValue * 100) - (real * 100 + dezenaCentavos * 10));

		numberPickerReal.setCurrent(real);
		numberPickerDezenaCentavo.setCurrent(dezenaCentavos);
		numberPickerCentavo.setCurrent(centavos);

	}

}
