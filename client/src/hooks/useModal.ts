import { useCallback } from 'react'
import { useRecoilState } from 'recoil'
import { ModalSubmitDataType, modalState } from '../store/store'
import { GroupMemberListType } from '../types/planDetailTypes'

type OpenModalType = {
  title: string
  description: string
  placeholder: string
  submitButton: string
  groupMember?: GroupMemberListType
  onSubmit: (modalSubmitData: ModalSubmitDataType | string) => void
}

const useModal = () => {
  const [modalData, setModalData] = useRecoilState(modalState)
  const openModal = useCallback(
    ({
      title,
      description,
      placeholder,
      submitButton,
      onSubmit,
      groupMember = [],
    }: OpenModalType) => {
      setModalData({
        isOpen: true,
        title,
        description,
        placeholder,
        groupMember,
        submitButton,
        onSubmit,
      })
    },
    [setModalData]
  )

  const closeModal = useCallback(() => {
    setModalData(prev => {
      return { ...prev, isOpen: false }
    })
  }, [setModalData])

  return { modalData, openModal, closeModal }
}

export default useModal
