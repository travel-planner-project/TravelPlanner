import { useState } from 'react'
import Modal from '../../Common/Modal'

const InviteModalObj = {
  title: '친구초대',
  description: '여행을 함께할 친구를 초대해보세요',
  placeholder: '친구의 이메일을 입력하세요',
  submitButton: '초대',
  isSearchBtn: true,
}

function InviteModal() {
  const [modalOpen, setModalOpen] = useState(false)

  return (
    <>
      <button type='button' onClick={() => setModalOpen(true)}>
        openModal
      </button>

      <Modal isOpen={modalOpen} onClose={() => setModalOpen(false)} {...InviteModalObj} />
    </>
  )
}

export default InviteModal
