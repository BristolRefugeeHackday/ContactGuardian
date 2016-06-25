package com.elroid.contactguardian.util;

import java.util.Comparator;

/**
 * Class: com.elroid.contactguardian.util.LabelValue
 * Project: Contact Guardian
 * Created Date: 25/06/2016 14:57
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class LabelValue implements CharSequence
{
	private static transient Logger logger = Logger.getLogger("LabelValue");

	protected String label;
	private String val;

	public LabelValue() {
	}

	public LabelValue(CharSequence label) {
		setLabel(s(label));
		setValue(s(label));
	}

	public LabelValue(CharSequence label, CharSequence value) {
		setLabel(s(label));
		setValue(s(value));
	}
	public LabelValue(CharSequence label, int value) {
		setLabel(s(label));
		setValue(""+value);
	}
	public LabelValue(CharSequence label, boolean value) {
		this(label, value ? 1 : 0);
	}

	private String s(CharSequence ch){
		if(ch == null) return null;
		else return ch.toString();
	}

	public String getName() {
		return getLabel();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return val;
	}

	public int getValueInt(){
		return Integer.parseInt(getValue());
	}

	public boolean isValueInt(){
		return val != null && Character.isDigit(val.charAt(0));
	}

	public boolean isValue(){
		return "1".equals(val);
	}

	public void setValue(String value) {
		this.val = value;
	}
	public void setValue(int value) {
		this.val = ""+value;
	}

	@Override
	public String toString() {
		return getLabel();
	}

	public String toStringPair() {
		return getLabel()+"="+getValue();
	}

	@Override
	public char charAt(int i) {
		return toString().charAt(i);
	}

	@Override
	public int length() {
		if(getLabel() == null) return 0;
		return getLabel().length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return toString().subSequence(start, end);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof LabelValue)) return false;

		LabelValue that = (LabelValue) o;

		return getValue().equals(that.getValue());
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	public String toStringFull() {
		final StringBuffer sb = new StringBuffer();
		sb.append("LabelValue");
		sb.append("{label='").append(getLabel()).append('\'');
		sb.append(", value='").append(getValue()).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public static class Alphabetizer implements Comparator<LabelValue>
	{
		@Override
		public int compare(LabelValue l1, LabelValue l2) {
			return l1.toStringPair().compareTo(l2.toStringPair());
		}
	}
}
