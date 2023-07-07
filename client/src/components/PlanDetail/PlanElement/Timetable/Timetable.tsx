import { useState } from 'react'
import Icon from '../../../Common/Icon'
import Timeline from './Timeline'
import styles from './Timetable.module.scss'

function Timetable() {
  const [timelines, setTimeLines] = useState<number[]>([1])
  const handleTimelineCounts = (type: 'plus' | 'minus', line?: number) => {
    if (type === 'plus') {
      return setTimeLines(pre => [...pre, pre[pre.length - 1] + 1])
    }
    return setTimeLines(pre => pre.filter(el => el !== line))
  }

  const disabledMinusBtn = timelines.length === 1

  return (
    <div className={styles.container}>
      {timelines.map(line => (
        <Timeline
          key={line}
          handleTimelineCounts={handleTimelineCounts}
          line={line}
          disabledMinusBtn={disabledMinusBtn}
        />
      ))}

      <button type='button' className={styles.plusBtn} onClick={() => handleTimelineCounts('plus')}>
        <Icon name='plus-square' />
      </button>
    </div>
  )
}

export default Timetable
