package net.sf.openrocket.gui.adaptors;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import net.sf.openrocket.preset.ComponentPreset;
import net.sf.openrocket.rocketcomponent.RocketComponent;
import net.sf.openrocket.startup.Application;

public class BodyTubePresetModel extends AbstractListModel implements ComboBoxModel {

	private final RocketComponent component;

	private List<ComponentPreset> presets;
	
	public BodyTubePresetModel(RocketComponent component) {
		presets = Application.getDaos().getBodyTubePresetDao().listAll();
		this.component = component;
	}
	
	public static class BodyTubePresetAdapter {
		// If the ComponentPreset bt is null, then no preset is selected.
		private ComponentPreset bt;
		private BodyTubePresetAdapter( ComponentPreset bt ) {
			this.bt = bt;
		}
		@Override
		public String toString() {
			if ( bt != null ) {
				return bt.getManufacturer() + " " + bt.getPartNo();
			} else {
				return "";
			}
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((bt == null) ? 0 : bt.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BodyTubePresetAdapter other = (BodyTubePresetAdapter) obj;
			if (bt == null) {
				if (other.bt != null)
					return false;
			} else if (!bt.equals(other.bt))
				return false;
			return true;
		}
	}
	
	@Override
	public int getSize() {
		return presets.size();
	}

	@Override
	public Object getElementAt(int index) {
		if ( index < 0 ) {
			return null;
		}
		return new BodyTubePresetAdapter(presets.get(index));
	}

	@Override
	public void setSelectedItem(Object anItem) {
		BodyTubePresetAdapter selected = (BodyTubePresetAdapter) anItem;
		if ( selected == null ) {
			component.loadPreset(null);
		} else {
			component.loadPreset(selected.bt);
		}
	}

	@Override
	public Object getSelectedItem() {
		ComponentPreset preset = (ComponentPreset) component.getPresetComponent();
		if ( preset == null ) {
			return null;
		} else {
			return new BodyTubePresetAdapter(preset);
		}
	}

}
