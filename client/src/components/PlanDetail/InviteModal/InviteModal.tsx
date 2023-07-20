import { useState } from 'react'
import FriendInfo, { FriendType } from './FriendInfo'
import Modal from '../../Common/Modal'

const Friend = {
  id: 123,
  profileImg: 'https://i.ytimg.com/vi/y9bWhYGvBlk/maxresdefault.jpg',
  userNickname: '닉네임',
  email: 'test123@naver.com',
}

function InviteModal() {
  const [modalOpen, setModalOpen] = useState(false)
  const [friend, setFriend] = useState<FriendType>(Friend)

  return (
    <>
      <button type='button' onClick={() => setModalOpen(true)}>
        openModal
      </button>

      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)}>
        <Modal.Title>친구초대</Modal.Title>
        <Modal.SubTitle>여행을 함께할 친구를 초대해보세요</Modal.SubTitle>
        <Modal.Body placeholder='친구의 이메일을 입력하세요' isSearchBtn />
        {friend.id && <FriendInfo friend={friend} />}
        <Modal.Footer>
          <Modal.SubmitButton>초대</Modal.SubmitButton>
          <Modal.CancelButton />
        </Modal.Footer>
      </Modal>
    </>
  )
}

export default InviteModal
