import MinusBtn, { MinusBtnProps } from './MinusBtn'
import Time from './Time'
import styles from './Timeline.module.scss'

const HourOptions = Array.from({ length: 24 }, (_, idx) => String(idx).padStart(2, '0'))
const MinuteOptions = Array.from({ length: 6 }, (_, idx) => String(idx * 10).padStart(2, '0'))

function Timeline({ ...props }: MinusBtnProps) {
  return (
    <div className={styles.container}>
      <Time dropDownOptions={HourOptions} />
      :
      <Time dropDownOptions={MinuteOptions} />
      <input type='text' className={styles.input} />
      <MinusBtn {...props} />
    </div>
  )
}

export default Timeline
