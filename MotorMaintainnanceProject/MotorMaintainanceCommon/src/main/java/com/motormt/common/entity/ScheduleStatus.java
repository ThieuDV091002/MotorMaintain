package com.motormt.common.entity;

public enum ScheduleStatus {
	PROCESSING{
		@Override
		public String defaultDescription() {
			return "Yêu cầu đang chờ được xử lý";
		}
	}, ACCEPTED{
		@Override
		public String defaultDescription() {
			return "Yêu cầu được chấp nhận";
		}
	}, REJECTED{
		@Override
		public String defaultDescription() {
			return "Yêu cầu bị từ chối";
		}
	};
	
	public abstract String defaultDescription();
}
