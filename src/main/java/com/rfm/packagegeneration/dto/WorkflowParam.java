package com.rfm.packagegeneration.dto;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class WorkflowParam  implements Serializable, SQLData {

	/**
     *
     */
	private static final long serialVersionUID = 557314059390880992L;
	private static final String typeName = "TY_POS_WRKFLWPARM";
	private Long parm_id;
	private String parm_na;
	private String parm_typ;
	private String val;

	/**
	 * @return typeName
	 * @throws SQLException Exception
	 */
	public String getSQLTypeName() throws SQLException{
		return WorkflowParam.typeName;
	}

	/**
	 * @param stream SQLInput
	 * @param typeName String
	 * @throws SQLException Exception
	 */
	public void readSQL(final SQLInput stream, final String typeName) throws SQLException{
		parm_id = stream.readLong();
		parm_na = stream.readString();
		parm_typ = stream.readString();
		val = stream.readString();
	}

	/**
	 * @param stream SQLOutput
	 * @throws SQLException Exception
	 */
	public void writeSQL(final SQLOutput stream) throws SQLException{
		stream.writeLong(parm_id);
		stream.writeString(parm_na);
		stream.writeString(parm_typ);
		stream.writeString(val);
	}

	/**
	 * @return the parm_id
	 */
	public Long getParm_id(){
		return parm_id;
	}

	/**
	 * @param parm_id the parm_id to set
	 */
	public void setParm_id(final Long parm_id){
		this.parm_id = parm_id;
	}

	/**
	 * @return the parm_na
	 */
	public String getParm_na(){
		return parm_na;
	}

	/**
	 * @param parm_na the parm_na to set
	 */
	public void setParm_na(final String parm_na){
		this.parm_na = parm_na;
	}

	/**
	 * @return the parm_typ
	 */
	public String getParm_typ(){
		return parm_typ;
	}

	/**
	 * @param parm_typ the parm_typ to set
	 */
	public void setParm_typ(final String parm_typ){
		this.parm_typ = parm_typ;
	}

	/**
	 * @return the val
	 */
	public String getVal(){
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(final String val){
		this.val = val;
	}
}
