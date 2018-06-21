package com.dl.activity.model;

import javax.persistence.Id;

import lombok.Data;

@Data
public class MatchInfo {
	/**
	 * 赛事id
	 */
	@Id
	private Integer matchId;

	/**
	 * 主队id
	 */
	private Integer homeTeamId;

	/**
	 * 主队logo
	 */
	private String homeTeamPic;

	/**
	 * 主队简称
	 */
	private String homeTeamAbbr;

	/**
	 * 客队id
	 */
	private Integer visitingTeamId;

	/**
	 * 客队logo
	 */
	private String visitingTeamPic;

	/**
	 * 客队简称
	 */
	private String visitingTeamAbbr;
}