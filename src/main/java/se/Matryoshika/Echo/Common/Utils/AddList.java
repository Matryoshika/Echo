package se.Matryoshika.Echo.Common.Utils;

import java.util.ArrayList;
import java.util.Collection;

public class AddList<E> extends ArrayList {

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	@Override
	public Object remove(int index) {
		return null;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {

	}

	@Override
	public boolean retainAll(Collection c) {
		return false;
	}

}
