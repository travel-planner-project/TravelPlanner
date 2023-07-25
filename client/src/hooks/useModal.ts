import { useCallback } from 'react'
import { useRecoilState } from 'recoil'
import { modalState } from '../store/store'

type OpenModalType = {
  title: string
  description: string
  placeholder: string
  submitButton: string
  isSearchBtn?: boolean
  onSubmit: (input: string) => void
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
      isSearchBtn = false,
    }: OpenModalType) => {
      setModalData({
        isOpen: true,
        title,
        description,
        placeholder,
        submitButton,
        onSubmit,
        isSearchBtn,
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
