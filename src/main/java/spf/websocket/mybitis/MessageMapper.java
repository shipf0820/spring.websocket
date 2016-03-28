package spf.websocket.mybitis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import spf.websocket.beans.Message;
import spf.websocket.beans.SendVo;

@Repository
public interface MessageMapper {
	
	void insertMessage(Message message);
	
	List<SendVo> selectUnReadMessageByUserId(@Param("receiverId")  String receiverId);
	
	void updateMessagesStatus(@Param("id")  Long id);
	
	void updateMessagesStatusByIds(@Param("ids") List<Long> ids);
	
	int selectUnReadCountByUserId(@Param("receiverId") String receiverId);
}
