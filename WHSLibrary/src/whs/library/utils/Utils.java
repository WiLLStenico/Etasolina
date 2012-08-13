/**
 * 
 */
package whs.library.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author WiLL
 * 
 */
public class Utils {

	/**
	 * @param view
	 *            desaloca views desnecessárias da memória quando aplicação é
	 *            encerrada e quando é alterada a orientacao da tela
	 */
	public static void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}

}
