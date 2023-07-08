import Icon from '../../../Common/Icon'
import Time from './Time'
import styles from './Timeline.module.scss'

type TimelineProps = {
  handleTimelineCounts: (type: 'plus' | 'minus', line: number) => void
  line: number
  disabledMinusBtn: boolean
}

const HourOptions = Array.from({ length: 24 }, (_, idx) => String(idx).padStart(2, '0'))
const MinuteOptions = Array.from({ length: 6 }, (_, idx) => String(idx * 10).padStart(2, '0'))

function Timeline({ handleTimelineCounts, line, disabledMinusBtn }: TimelineProps) {
  return (
    <div className={styles.container}>
      <Time dropDownOptions={HourOptions} />
      :
      <Time dropDownOptions={MinuteOptions} />
      <input type='text' className={styles.input} />
      <button
        type='button'
        onClick={() => handleTimelineCounts('minus', line)}
        className={disabledMinusBtn ? styles.disabledBtn : ''}
        disabled={disabledMinusBtn}
      >
        <Icon name='minus-square' />
      </button>
    </div>
  )
}

export default Timeline
