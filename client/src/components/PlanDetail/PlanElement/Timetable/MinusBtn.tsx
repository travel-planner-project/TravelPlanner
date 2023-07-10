import Icon from '../../../Common/Icon'
import styles from './MinusBtn.module.scss'

export type MinusBtnProps = {
  deleteTimeline: () => void
  disableMinusBtn: boolean
}

function MinusBtn({ deleteTimeline, disableMinusBtn }: MinusBtnProps) {
  return (
    <button
      type='button'
      onClick={deleteTimeline}
      className={disableMinusBtn ? styles.disabledBtn : ''}
      disabled={disableMinusBtn}
    >
      <Icon name='minus-square' size={12} />
    </button>
  )
}

export default MinusBtn
