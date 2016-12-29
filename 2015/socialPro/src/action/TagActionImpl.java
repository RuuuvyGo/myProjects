package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import socialProExceptions.DAOException;
import dao.DAO;
import dao.TagDAO;
import dao.TagDetailsDAO;
import dto.TagDTO;
import dto.TagDetailsDTO;
import dto.TeamTagsVDTO;
import factory.DAOFactory;

public class TagActionImpl extends BaseAction implements TagSearchAction,TagInsertAction,TagDropAction,TagUpdateAction{
	
	public TagActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((TagDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("TagDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(TagDAO)dao;
	}
	
	@Override
	public boolean updateTag(String sourceCode, List<String> newTagNameList)
			throws DAOException {
		// TODO Auto-generated method stub
		
		
		//1. get newTagNameList's newTagCodeList
		int length = newTagNameList.size();
		List<String> newTagCodeList = new ArrayList<String>();
		String code;
		int failCnt=0;
		for(int i=0;i<length;i++){
			TagDTO dto=((TagDAO)super.getDAO()).searchTagName(newTagNameList.get(i));
			if(dto==null){
				code=((TagDAO)super.getDAO()).insertTag(new TagDTO(newTagNameList.get(i)));
				if(code==null) failCnt++;
				else newTagCodeList.add(code);
			}
			else newTagCodeList.add(dto.getTagCode());
		}
		
		//2. update taglist for sourceCode
		((TagDetailsDAO)super.getDAO()).deleteTagDetailsSCode(sourceCode);
		for(int i=0;i<length;i++){
			if(!((TagDetailsDAO)super.getDAO()).insertTagDtl(new TagDetailsDTO(sourceCode,newTagCodeList.get(i))))return false;
		}
		
		return true;
	}

	@Override
	public boolean dropTagCode(String tagCode) throws DAOException {
		// TODO Auto-generated method stub
		
		//1. drop tagDetails
		int res=((TagDetailsDAO)super.getDAO()).deleteTagDetailsTCode(tagCode);
		if(res==0)return false;
		
		//2. drop tag
		if(!((TagDAO)super.getDAO()).deleteTagCode(tagCode))return false;
		return false;
	}

	@Override
	public boolean dropTagName(String tagName) throws DAOException {
		// TODO Auto-generated method stub
		
		//1. find tagCode
		String tagCode = ((TagDAO)super.getDAO()).searchTagName(tagName).getTagCode();
		if(tagCode==null)return false;
		
		return this.dropTagCode(tagCode);
	}

	@Override
	public int dropTagCode(String tagCode, String front) throws DAOException {
		// TODO Auto-generated method stub
		
		return ((TagDetailsDAO)super.getDAO()).deleteTagDetailsTCode(tagCode, front);
	}

	@Override
	public int dropTagName(String tagName, String front) throws DAOException {
		// TODO Auto-generated method stub
		
		//1. find tagCode
		if(front.equals("project")){
			
		}else if(front.equals("team")){
			List<TeamTagsVDTO> dtoList=new TeamTagsVActionImpl().searchTagName(tagName);
			int cnt=dtoList.size();
			if(cnt==0)return 0;
			List<String> sourceCodeList = new ArrayList<String>();
			for(int i=0;i<cnt;i++){
				sourceCodeList.add(dtoList.get(i).getTeamCode());
			}
			if(((TagDetailsDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("TagDetailsDAO")).deleteTagDetailsCodes(dtoList.get(0).getTagCode(), sourceCodeList))return cnt;
			return 0;
		}else if(front.equals("board")){
			
		}
		
		return 1;
	}

	@Override
	public boolean dropTagCodesC(String tagCode, String sourceCode)
			throws DAOException {
		// TODO Auto-generated method stub
		
		return ((TagDetailsDAO)super.getDAO()).deleteTagDetailsCodes(tagCode, sourceCode);
	}

	@Override
	public boolean dropTagNamesC(String tagName, String sourceCode)
			throws DAOException {
		// TODO Auto-generated method stub
		String tagCode = ((TagDAO)super.getDAO()).searchTagName(tagName).getTagCode();
		if(tagCode==null)return false;
		
		return this.dropTagCodesC(tagCode, sourceCode);
	}

	@Override
	public int dropTagSCode(String sourceCode) throws DAOException {
		// TODO Auto-generated method stub
		return ((TagDetailsDAO)super.getDAO()).deleteTagDetailsSCode(sourceCode);
	}

	public int insertTagDetails(List<String> tagCodeList, String sourceCode)throws DAOException{
		
		List<TagDetailsDTO> dtoList = new ArrayList<TagDetailsDTO>();
		for(int i=0;i<tagCodeList.size();i++){
			dtoList.add(new TagDetailsDTO(tagCodeList.get(i),sourceCode));
		}
		return ((TagDetailsDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("TagDetailsDAO")).insertTagDtl(dtoList);
	}
	
	@Override
	public boolean insertTag(String tagName, String sourceCode)
			throws DAOException {
		// TODO Auto-generated method stub
		
		TagDTO dto=((TagDAO)super.getDAO()).searchTagName(tagName);
		if(dto==null){
			String tagCode=((TagDAO)super.getDAO()).insertTag(new TagDTO(tagName));
			if(tagCode==null)return false;
			return ((TagDetailsDAO)super.getDAO()).insertTagDtl(new TagDetailsDTO(tagCode,sourceCode));
		}else{
			return ((TagDetailsDAO)super.getDAO()).insertTagDtl(new TagDetailsDTO(dto.getTagCode(),sourceCode));
		}
	}
	
	@Override
	public int insertTag(List<String> tagNameList, String sourceCode)
			throws DAOException {
		// TODO Auto-generated method stub
		
		//1. insert into tagTB
		int cnt = tagNameList.size();
		List<TagDTO> dtoList = new ArrayList<TagDTO>();
		for(int i=0;i<cnt;i++){
			dtoList.add(new TagDTO(tagNameList.get(i)));
		}
		List<String> tagCodes=((TagDAO)this.getDAO()).insertTag(dtoList);
		if(tagCodes.isEmpty())return 0;
		
		System.out.println("data is cleared");
		//2. insert into tagDetailsTB
		cnt=tagCodes.size();
		List<TagDetailsDTO> dtoDList = new ArrayList<TagDetailsDTO>();
		for(int i=0;i<cnt;i++){
			dtoDList.add(new TagDetailsDTO(tagCodes.get(i),sourceCode));
		}
		return ((TagDetailsDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("TagDetailsDAO")).insertTagDtl(dtoDList);
	}
	
	public Map<String,List<String>> searchTagsExist(List<String> tagName)throws DAOException{
		System.out.println("exist");
		Map<String, List<String>> resMap = new HashMap<String,List<String>>();
		List<String> existList = new ArrayList<String>();
		List<String> nonexistList = new ArrayList<String>();
		int cnt= tagName.size();
		for(int i=0;i<cnt;i++){
			TagDTO dto =((TagDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("TagDAO")).searchTagName(tagName.get(i));
			if(dto==null){
				System.out.println(tagName.get(i));
				nonexistList.add(tagName.get(i));
			}else existList.add(dto.getTagCode());
		}
		resMap.put("exist", existList);
		resMap.put("nonexist", nonexistList);
		System.out.println("exist end");
		return resMap;
	}

	@Override
	public Map<String, List<String>> searchTag(String tagName)
			throws DAOException {
		// TODO Auto-generated method stub
		
		//1.find tagCode
		TagDTO dto=((TagDAO)super.getDAO()).searchTagName(tagName);
		if(dto==null)return null;
		
		Map<String,List<String>> resMap  = new HashMap<String, List<String>>();
		List<String> keyList = new ArrayList<String>();
		keyList.add("project");keyList.add("team");keyList.add("board");keyList.add("code");
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		int leng=0;
		for(int i=0;i<4;i++){
			dtoList=((TagDetailsDAO)super.getDAO()).searchTagDetails(dto.getTagCode(), keyList.get(i));
			leng=dtoList.size();
			for(int k=0;k<leng;k++){
				valList.add(dtoList.get(k).getSourceCode());
			}
			resMap.put(keyList.get(i), valList);
		}
		return resMap;
	}

	@Override
	public Map<String, List<String>> searchTag(List<String> tagName)
			throws DAOException {
		// TODO Auto-generated method stub
		
		Map<String, List<String>> resMap = new HashMap<String, List<String>>();
		
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		List<String> keyList = new ArrayList<String>();
		keyList.add("project");keyList.add("team");keyList.add("board");keyList.add("code");
		List<String> tagCodeList = new ArrayList<String>();
		int leng=tagName.size();
		
		for(int i=0;i<leng;i++){
			tagCodeList.add(((TagDAO)super.getDAO()).searchTagName(tagName.get(i)).getTagCode());
		}
		
		for(int i=0;i<4;i++){
			dtoList = ((TagDetailsDAO)super.getDAO()).searchTagDetails(tagCodeList, keyList.get(i));
			
			if(dtoList.size()==0)return null;
			int s = dtoList.size();
			for(int k=0;k<s;k++){
				valList.add(dtoList.get(k).getSourceCode());
			}
			resMap.put(keyList.get(i), valList);
		}
		return null;
	}

	@Override
	public List<String> searchTag(String tagName, String key)
			throws DAOException {
		// TODO Auto-generated method stub
		//1.find tagCode
		TagDTO dto=((TagDAO)super.getDAO()).searchTagName(tagName);
		if(dto==null)return null;
		
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		int leng=0;

		dtoList=((TagDetailsDAO)super.getDAO()).searchTagDetails(dto.getTagCode(), key);
		leng=dtoList.size();
		for(int k=0;k<leng;k++){
			valList.add(dtoList.get(k).getSourceCode());
		}
				
		return valList;
	}

	@Override
	public List<String> searchTag(List<String> tagName, String key)
			throws DAOException {
		// TODO Auto-generated method stub

		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		List<String> tagCodeList = new ArrayList<String>();
		int leng=tagName.size();
		
		for(int i=0;i<leng;i++){
			tagCodeList.add(((TagDAO)super.getDAO()).searchTagName(tagName.get(i)).getTagCode());
		}
		

		dtoList = ((TagDetailsDAO)super.getDAO()).searchTagDetails(tagCodeList, key);
		
		if(dtoList.size()==0)return null;
		int s = dtoList.size();
		for(int k=0;k<s;k++){
			valList.add(dtoList.get(k).getSourceCode());
		}
		
		return valList;
	}

	@Override
	public List<String> searchTagSCode(String sourceCode) throws DAOException {
		// TODO Auto-generated method stub
		List<String> tagList = new ArrayList<String>();
		List<TagDetailsDTO> dtoList = new ArrayList<TagDetailsDTO>();
		dtoList = ((TagDetailsDAO)super.getDAO()).searchTagDetailsSCode(sourceCode);
		int leng = dtoList.size();
		
		if(leng==0)return null;
		
		for(int i=0;i<leng;i++){
			tagList.add(dtoList.get(i).getTagCode());
		}
		
		return tagList;
	}

	@Override
	public Map<String, List<String>> searchTagCode(String tagCode)
			throws DAOException {
		// TODO Auto-generated method stub
		Map<String,List<String>> resMap  = new HashMap<String, List<String>>();
		List<String> keyList = new ArrayList<String>();
		keyList.add("project");keyList.add("team");keyList.add("board");keyList.add("code");
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		int leng=0;
		for(int i=0;i<4;i++){
			dtoList=((TagDetailsDAO)super.getDAO()).searchTagDetails(tagCode, keyList.get(i));
			leng=dtoList.size();
			for(int k=0;k<leng;k++){
				valList.add(dtoList.get(k).getSourceCode());
			}
			resMap.put(keyList.get(i), valList);
		}
		return resMap;
	}

	@Override
	public Map<String, List<String>> searchTagCode(List<String> tagCode)
			throws DAOException {
		// TODO Auto-generated method stub
		//1.find tagCode
		
		Map<String,List<String>> resMap  = new HashMap<String, List<String>>();
		List<String> keyList = new ArrayList<String>();
		keyList.add("project");keyList.add("team");keyList.add("board");keyList.add("code");
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		int leng=0;
		for(int i=0;i<4;i++){
			dtoList=((TagDetailsDAO)super.getDAO()).searchTagDetails(tagCode.get(i), keyList.get(i));
			leng=dtoList.size();
			for(int k=0;k<leng;k++){
				valList.add(dtoList.get(k).getSourceCode());
			}
			resMap.put(keyList.get(i), valList);
		}
		return resMap;
	}

	@Override
	public List<String> searchTagCode(String tagCode, String key)
			throws DAOException {
		// TODO Auto-generated method stub
		//1.find tagCode
		
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		int leng=0;

		dtoList=((TagDetailsDAO)super.getDAO()).searchTagDetails(tagCode, key);
		leng=dtoList.size();
		for(int k=0;k<leng;k++){
			valList.add(dtoList.get(k).getSourceCode());
		}
				
		return valList;
	}

	@Override
	public List<String> searchTagCode(List<String> tagCode, String key)
			throws DAOException {
		// TODO Auto-generated method stub
		List<TagDetailsDTO> dtoList;
		List<String> valList = new ArrayList<String>();
		List<String> tagCodeList = new ArrayList<String>();
		
		dtoList = ((TagDetailsDAO)super.getDAO()).searchTagDetails(tagCodeList, key);
		
		if(dtoList.size()==0)return null;
		int s = dtoList.size();
		for(int k=0;k<s;k++){
			valList.add(dtoList.get(k).getSourceCode());
		}
		
		return valList;
	}

}
