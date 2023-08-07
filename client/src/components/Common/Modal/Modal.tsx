import { useEffect, useMemo, useState } from 'react'
import styles from './Modal.module.scss'
import useModal from '../../../hooks/useModal'
import InviteModal from './InviteModal'

type ModalContentProp = {
  onClose: () => void
  title: string
  description: string
  placeholder: string
  submitButton: string
  onSubmit: (input: string) => void
}

function ModalContent({
  onClose,
  title,
  description,
  placeholder,
  submitButton,
  onSubmit,
}: ModalContentProp) {
  const [inputValue, setInputValue] = useState('')

  const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault()
    onSubmit(inputValue)
    // const { status, data } = await onSubmit(inputValue)
    // if (status !== 200) { alert(data.message) }

    onClose()
  }

  return (
    <>
      <label htmlFor={title} className={styles.title}>
        {title}
      </label>
      <div className={styles.description}>{description}</div>
      <div className={styles.inputSearchBox}>
        <input
          id={title}
          className={styles.input}
          placeholder={placeholder}
          value={inputValue}
          onChange={event => setInputValue(event.target.value)}
        />
      </div>
      <div className={styles.btnBox}>
        <button type='submit' className={styles.submitBtn} onClick={handleSubmit}>
          {submitButton}
        </button>
        <button type='button' className={styles.cancelBtn} onClick={onClose}>
          취소
        </button>
      </div>
    </>
  )
}

type ModalProps = {
  type: 'invite' | 'create-planner'
}

function Modal({ type }: ModalProps) {
  const { modalData, closeModal } = useModal()

  const handleEscBtn = (event: KeyboardEvent) => {
    const key = event.key || event.keyCode
    if (key === 'Escape' || key === 27) {
      closeModal()
    }
  }

  useEffect(() => {
    document.addEventListener('keydown', handleEscBtn)
    return () => document.removeEventListener('keydown', handleEscBtn)
  }, [])

  const modalContent = useMemo(() => {
    switch (type) {
      case 'invite':
        return <InviteModal onClose={closeModal} {...modalData} />
      case 'create-planner':
        return <ModalContent onClose={closeModal} {...modalData} />
      default: {
        return <ModalContent onClose={closeModal} {...modalData} />
      }
    }
  }, [type, modalData])

  return (
    modalData.isOpen && (
      <div className={styles.background} onClick={closeModal} role='presentation'>
        <div
          className={styles.wrapper}
          onClick={(e: React.MouseEvent) => e.stopPropagation()}
          role='presentation'
        >
          <form className={styles.content}>{modalContent}</form>
        </div>
      </div>
    )
  )
}

export default Modal
