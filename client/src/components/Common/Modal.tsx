import { ReactNode, useEffect, useContext, createContext } from 'react'
import styles from './Modal.module.scss'

type ModalProp = {
  children: ReactNode
  isOpen: boolean
  onClose: () => void
}

const ModalContext = createContext({ onClose: () => {} })

function Modal({ children, isOpen, onClose }: ModalProp) {
  const handleEscBtn = (event: KeyboardEvent) => {
    const key = event.key || event.keyCode
    if (key === 'Escape' || key === 27) {
      onClose()
    }
  }

  useEffect(() => {
    document.addEventListener('keydown', handleEscBtn)
    return () => document.removeEventListener('keydown', handleEscBtn)
  }, [])

  return (
    isOpen && (
      <div className={styles.background} onClick={onClose} role='presentation'>
        <div
          className={styles.wrapper}
          onClick={(e: React.MouseEvent) => e.stopPropagation()}
          role='presentation'
        >
          <div className={styles.content}>
            <ModalContext.Provider
              // eslint-disable-next-line react/jsx-no-constructed-context-values
              value={{ onClose }}
            >
              {children}
            </ModalContext.Provider>
          </div>
        </div>
      </div>
    )
  )
}

export default Modal

type ModalChildrenProp = {
  children: ReactNode
}

function ModalTitle({ children }: ModalChildrenProp) {
  return <div className={styles.title}>{children}</div>
}
function ModalSubTitle({ children }: ModalChildrenProp) {
  return <div className={styles.subTitle}>{children}</div>
}

type BodyProp = {
  isSearchBtn?: boolean
  placeholder: string
}

function ModalBody({ placeholder, isSearchBtn = false }: BodyProp) {
  return (
    <div>
      <div className={styles.inputSearchBox}>
        <input className={styles.input} placeholder={placeholder} />
        {isSearchBtn && (
          <button type='button' className={styles.searchBtn}>
            검색
          </button>
        )}
      </div>
    </div>
  )
}
function ModalFooter({ children }: ModalChildrenProp) {
  return <div className={styles.footer}>{children}</div>
}

function CancelButton() {
  const { onClose } = useContext(ModalContext)

  return (
    <button type='button' className={styles.cancelBtn} onClick={onClose}>
      취소
    </button>
  )
}
function SubmitButton({ children }: ModalChildrenProp) {
  return (
    <button type='button' className={styles.submitBtn}>
      {children}
    </button>
  )
}

Modal.Title = ModalTitle
Modal.SubTitle = ModalSubTitle
Modal.Body = ModalBody
Modal.Footer = ModalFooter
Modal.CancelButton = CancelButton
Modal.SubmitButton = SubmitButton
