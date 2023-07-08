import Icon from '../../../Common/Icon'
import styles from './MinusBtn.module.scss'

export type MinusBtnProps = {
  deleteTimeline: () => void
  disabledMinusBtn: boolean
}

function MinusBtn({ deleteTimeline, disabledMinusBtn }: MinusBtnProps) {
  return (
    <button
      type='button'
      onClick={deleteTimeline}
      className={disabledMinusBtn ? styles.disabledBtn : ''}
      disabled={disabledMinusBtn}
    >
      <Icon name='minus-square' />
    </button>
  )
}

export default MinusBtn
