import useModal from '../../../hooks/useModal'
import Modal from '../../Common/Modal/Modal'

const InviteModalObj = {
  title: '친구초대',
  description: '여행을 함께할 친구를 초대해보세요',
  placeholder: '친구의 이메일을 입력하세요',
  submitButton: '초대',
  isSearchBtn: true,
  onSubmit: (input: string) => {
    console.log(input, '친구 초대 api request')
  },
}

function InviteModal() {
  const { openModal } = useModal()

  return (
    <>
      <button type='button' onClick={() => openModal(InviteModalObj)}>
        openModal
      </button>
      <Modal />
    </>
  )
}

export default InviteModal
