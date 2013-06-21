package chat.kata

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock


class ChatService {

	private final List<ChatMessage> messages = new ArrayList<ChatMessage>()
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock()

	/**
	 * Collects chat messages in the provided collection
	 * 
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 * 
	 * @return the sequence of the last message collected.
	 */
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		lock.readLock().lock()
		try {
			int firstMessage = fromSeq != null ? fromSeq : 0;
			collector.addAll(messages.subList(firstMessage, messages.size()))
			return messages.size()
		} finally {
		lock.readLock().unlock()
		}
	}

	/**
	 * Puts a new message at the bottom of the chat
	 * 
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		lock.writeLock().lock()
		try {
			messages.add(message)
		} finally {
			lock.writeLock().unlock()
		}
	}
}

